package learn.solarfarm.data;

import learn.solarfarm.domain.SolarPanelResult;
import learn.solarfarm.models.Material;
import learn.solarfarm.models.SolarPanel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SolarPanelFileRepository implements SolarPanelRepository {

    private final String filePath;
    private final String delimiter = ",";

    public SolarPanelFileRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<SolarPanel> findAll() throws XDataAccessException {
        ArrayList<SolarPanel> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String header = reader.readLine();
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                SolarPanel panel = lineToPanel(line);
                if (panel != null) {
                    result.add(panel);
                }
            }
        } catch (FileNotFoundException ex) {
            // If the file doesn't exist, it is created when a new panel is added.
        } catch (IOException ex) {
            throw new XDataAccessException("Could not open the file path: " + filePath, ex);
        }
        return result;
    }

    @Override
    public List<SolarPanel> findBySection(String section) throws XDataAccessException {
        List<SolarPanel> all = findAll();
        List<SolarPanel> matchingSection = new ArrayList<SolarPanel>();
        for (SolarPanel panel : all) {
            if (panel.getSection().equals(section)) {
                matchingSection.add(panel);
            }
        }
        return matchingSection;
    }

    @Override
    public SolarPanel findById(int panelId) throws XDataAccessException {
        List<SolarPanel> all = findAll();
        for (SolarPanel panel : all) {
            if (panel.getId() == panelId) {
                return panel;
            }
        }
        return null;
    }


    private String cleanField(String field) {
        return field.replace(delimiter, "")
                .replace("/r", "")
                .replace("/n", "");
    }

    private String panelToLine(SolarPanel panel) {
        StringBuilder buffer = new StringBuilder(100);
        buffer.append(panel.getId()).append(delimiter);
        buffer.append(cleanField(panel.getSection())).append(delimiter);
//        buffer.append(cleanField(panel.getId())).append(delimiter);
        buffer.append(panel.isTracking());
        return buffer.toString();
    }

    private void writeToFile(List<SolarPanel> panels) throws XDataAccessException {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.println("id,section,row,column,material,is_tracking");
            for (SolarPanel panel : panels) {
                writer.println(serialize(panel));
            }
        } catch (IOException ex) {
            throw new XDataAccessException("Could not write to file path: " + filePath, ex);
        }
    }

    // TODO DELETE?
    private String serialize(SolarPanel panel) {
        return String.format("%s,%s,%s,%s,%s,%s,%s",
                panel.getId(),
                panel.getSection(),
                panel.getRow(),
                panel.getColumn(),
                panel.getYear(),
                panel.getMaterial(),
                panel.isTracking());
    }

    @Override
    public SolarPanel add(SolarPanel panel) throws XDataAccessException {
        List<SolarPanel> all = findAll();
        all.add(panel);
        writeToFile(all);
        return panel;
    }

    @Override
    public boolean update(SolarPanel panel) throws XDataAccessException {
        List<SolarPanel> all = findAll();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getId() == panel.getId()) {
                all.set(i, panel);
                writeToFile(all);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteById(int panelId) throws XDataAccessException {
        List<SolarPanel> all = findAll();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getId() == panelId) {
                all.remove(i);
                writeToFile(all);
                return true;
            }
        }
        return false;
    }

    private SolarPanel lineToPanel(String line) {
        String[] fields = line.split(delimiter);

        if (fields.length != 7) {
            return null;
        }

        return new SolarPanel(
                Integer.parseInt(fields[0]),
                fields[1],
                Integer.parseInt(fields[2]),
                Integer.parseInt(fields[3]),
                Integer.parseInt(fields[4]),
                Material.valueOf(fields[5]),
                Boolean.parseBoolean(fields[6])
        );
    }
}