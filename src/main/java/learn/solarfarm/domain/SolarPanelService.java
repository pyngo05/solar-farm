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
        } else {
            List<SolarPanel> panelsWithMatchingSection = repository.findBySection(section);
            result.setPanels(panelsWithMatchingSection);
        }

        return result;
    }

    public SolarPanelResult add(SolarPanel panel) throws XDataAccessException {
        SolarPanelResult result = validateSolarPanel(panel);
        if (!result.isSuccess()) {
            return result;
        }
        result = validateSolarPanel(panel);
        if (!result.isSuccess()) {
            return result;
        }
        SolarPanel p = repository.add(panel);
        result.setPanel(p);
        return result;
    }

    // Returns true if the panel is valid.
    private SolarPanelResult validateSolarPanel(SolarPanel panel) {
        SolarPanelResult result = new SolarPanelResult();

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

        return result;
    }

    public SolarPanelResult update(SolarPanel panel) throws XDataAccessException {
        SolarPanelResult result = validateSolarPanel(panel);
        if (!result.isSuccess()) {
            return result;
        }

        SolarPanel existing = repository.findById(panel.getId());
        if(existing == null) {
            result.setErrorMessage("Panel id " + panel.getId() + " not found.");
            return result;
        }

        boolean success = repository.update(panel);
        if(!success) {
            result.setErrorMessage("Could not update panel.");
        }
        return result;
    }


//        public MemoryResult deleteById(int memoryId) throws XDataAccessException {
//            MemoryResult result = new MemoryResult();
//            if (!repository.deleteById(memoryId)) {
//                String message = String.format("Memory id %s was not found.", memoryId);
//                result.addErrorMessage(message);
//            }
//            return result;
//        }
//
//
}