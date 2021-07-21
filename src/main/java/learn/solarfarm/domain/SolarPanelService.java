package learn.solarfarm.domain;

import learn.solarfarm.data.SolarPanelRepository;
import learn.solarfarm.data.XDataAccessException;
import learn.solarfarm.data.SolarPanelRepository;
import learn.solarfarm.models.Material;
import learn.solarfarm.models.SolarPanel;

import java.time.LocalDate;
import java.util.List;

// Rules for the SolarPanel model
public class SolarPanelService {

    private final SolarPanelRepository repository;

    public SolarPanelService(SolarPanelRepository repository) {
        this.repository = repository;
    }

    public SolarPanelResult findBySection(String section) throws XDataAccessException {
        SolarPanelResult result = new SolarPanelResult();

        if (section.isEmpty()) {
            result.setErrorMessage("Section must not be empty.");
            return result;
        }

        List<SolarPanel> panelsWithMatchingSection = repository.findBySection(section);
        result.setPanels(panelsWithMatchingSection);

        return result;
    }

    public SolarPanelResult add(SolarPanel panel) throws XDataAccessException {
        SolarPanelResult result = validateSolarPanel(panel);
        if (!result.isSuccess()) {
            return result;
        }

        // check if panel with this section-row-column combination already exists
        result = findPanelBySectionRowColumn(panel.getSection(), panel.getRow(), panel.getColumn());
        if (result.isSuccess()) {
            result.setErrorMessage("Panel already exists with that section-row-column combination.");
            return result;
        }

        SolarPanel p = repository.add(panel);
        result = new SolarPanelResult();
        result.setPanel(p);

        return result;
    }

    // Returns true if the panel is valid.
    private SolarPanelResult validateSolarPanel(SolarPanel panel) {
        SolarPanelResult result = new SolarPanelResult();

        if (panel.getId() < 0) {
            result.setErrorMessage("ID must be greater than 0.");
        }

        if (panel.getSection() == null || panel.getSection().isBlank()) {
            result.setErrorMessage("Section is required.");
        }

        if (panel.getRow() < 0 || panel.getRow() > 250) {
            result.setErrorMessage("Row must be 1-250.");
        }

        if (panel.getColumn() < 0 || panel.getColumn() > 250) {
            result.setErrorMessage("Column must be 1-250.");
        }

        if (panel.getYear() > LocalDate.now().getYear()) {
            result.setErrorMessage("Year cannot be in the future.");
        }

        // Material and isTracking are implicitly valid due to their types, so don't need more validation.

        return result;
    }

    public SolarPanelResult update(int panelID, SolarPanel updatedPanel) throws XDataAccessException {
        SolarPanelResult result = new SolarPanelResult();

        // find existing panel
        SolarPanel existing = repository.findById(panelID);
        if(existing == null) {
            result.setErrorMessage("Panel id '" + panelID + "' not found.");
            return result;
        }

        // validate new panel info
        result = validateSolarPanel(updatedPanel);
        if (!result.isSuccess()) {
            return result;
        }

        // ensure ID did not change somehow
        if (updatedPanel.getId() != panelID) {
            result.setErrorMessage("Panel id cannot be changed.");
            return result;
        }

        // check if panel with this section-row-column combination already exists
        result = findPanelBySectionRowColumn(updatedPanel.getSection(), updatedPanel.getRow(), updatedPanel.getColumn());
        if (result.isSuccess()) {
            result.setErrorMessage("Panel already exists with that section-row-column combination.");
            return result;
        }

        // update repo
        boolean success = repository.update(updatedPanel);
        result = new SolarPanelResult();
        if(!success) {
            result.setErrorMessage("Could not update panel.");
            return result;
        }

        return result;
    }

    public SolarPanelResult deleteById(int panelId) throws XDataAccessException {
        SolarPanelResult result = new SolarPanelResult();

        SolarPanel panel = repository.findById(panelId);
        if (panel == null) {
            String message = String.format("Panel id %s was not found.", panelId);
            result.setErrorMessage(message);
            return result;
        }

        boolean success = repository.deleteById(panelId);
        if (!success) {
            String message = String.format("Panel id %s failed to delete.", panelId);
            result.setErrorMessage(message);
        }

        return result;
    }

    public SolarPanelResult findPanelBySectionRowColumn(String section, int row, int column) throws XDataAccessException {
        List<SolarPanel> all = repository.findAll();
        SolarPanelResult result = new SolarPanelResult();
        for (SolarPanel panel : all) {
            if (panel.getSection() == section && panel.getRow() == row && panel.getColumn() == column) {
                result.setPanel(panel);
                return result;
            }
        }
        result.setErrorMessage("Panel not found.");
        return result;
    }
}