package learn.solarfarm.data;

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

    //TODO CONT HERE

    @Override
    public List<SolarPanel> findAll() throws XDataAccessException {
        ArrayList<SolarPanel> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
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
    public SolarPanel findBySection(String section) throws XDataAccessException {
        List<SolarPanel> all = findAll();
        for (SolarPanel panel : all) {
            if (panel.getId() == panel.id) {
                return panel;
            }
        }
        return null;
    }

    @Override
    public SolarPanel add(SolarPanel panel) throws XDataAccessException {
        List<SolarPanel> all = findAll();
        int nextId = getNextId(all);
        panel.setId(nextId);
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
    public boolean deleteById(int memoryId) throws XDataAccessException {
        List<SolarPanel> all = findAll();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getId() == memoryId) {
                all.remove(i);
                writeToFile(all);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<SolarPanel> findIsTracking(boolean tracking) throws XDataAccessException {
        ArrayList<SolarPanel> result = new ArrayList<>();
        for (SolarPanel panel : findAll()) {
            if (panel.isTracking() == tracking) {
                result.add(panel);
            }
        }
        return result;
    }

    private SolarPanel lineToPanel(String line) {
        String[] fields = line.split(delimiter);

        if (fields.length != 6) {
            return null;
        }

        return new SolarPanel(
                Integer.parseInt(fields[0]),
                fields[1],
                fields[2],
                "true".equals(fields[3])
        );
    }

    private String panelToLine(SolarPanel panel) {
        StringBuilder buffer = new StringBuilder(100);
        buffer.append(panel.getId()).append(delimiter);
        buffer.append(cleanField(panel.getFrom())).append(delimiter);
        buffer.append(cleanField(panel.getContent())).append(delimiter);
        buffer.append(panel.isShareable());
        return buffer.toString();
    }

    private void writeToFile(List<SolarPanel> panel) throws XDataAccessException {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            for (SolarPanel panel : panel) {
                writer.println(panelToLine(panel));
            }
        } catch (IOException ex) {
            throw new XDataAccessException("Could not write to file path: " + filePath, ex);
        }
    }

    private String cleanField(String field) {
        return field.replace(delimiter, "")
                .replace("/r", "")
                .replace("/n", "");
    }
}