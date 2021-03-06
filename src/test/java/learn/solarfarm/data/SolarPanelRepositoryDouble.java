package learn.solarfarm.data;

import learn.solarfarm.models.Material;
import learn.solarfarm.models.SolarPanel;

import java.util.ArrayList;
import java.util.List;

public class SolarPanelRepositoryDouble implements SolarPanelRepository {

    private ArrayList<SolarPanel> panels = new ArrayList<>();

    public SolarPanelRepositoryDouble() {
        // create fake data
        panels.add(new SolarPanel(5, "Section1", 2, 3, 2018,
                Material.aSi, true));
        panels.add(new SolarPanel(6, "Section1", 3, 3, 2018,
                Material.aSi, true));
        panels.add(new SolarPanel(7, "Section2", 1, 1, 2018,
                Material.aSi, true));
    }

    @Override
    public List<SolarPanel> findAll() throws XDataAccessException {
        return panels;
    }

    @Override
    public List<SolarPanel> findBySection(String section) throws XDataAccessException {
        List<SolarPanel> matchingSection = new ArrayList<SolarPanel>();
        for (SolarPanel panel : panels) {
            if (panel.getSection().equals(section)) {
                matchingSection.add(panel);
            }
        }
        return matchingSection;
    }

    @Override
    public SolarPanel findById(int panelId) throws XDataAccessException {
        for (SolarPanel p : panels) {
            if (p.getId() == panelId) {
            return p;
        }
    }
        return null;
    }

    @Override
    public SolarPanel add(SolarPanel panel) throws XDataAccessException {
        panels.add(panel);
        return panel;
    }

    @Override
    public boolean update(SolarPanel panel) throws XDataAccessException {
        return true;
    }

    @Override
    public boolean deleteById(int panelId) throws XDataAccessException {
        return true;
    }


}