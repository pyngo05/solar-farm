package learn.solarfarm.domain;

import learn.solarfarm.models.SolarPanel;

import java.util.ArrayList;
import java.util.List;

// Return type for domain methods that may fail.
// If a method fails, the result contains an error message that explains the failure.
// If a method succeeds then the result contains a list of panels. The list may be empty,
// contain one SolarPanel, or contain multiple SolarPanels, depending on the method.
public class SolarPanelResult {

    private String errorMessage;
    private List<SolarPanel> panels;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String message) {
        errorMessage = message;
    }

    public boolean isSuccess() {
        // If an error message exists, the operation failed.
        return errorMessage == null || errorMessage.isEmpty();
    }

    public SolarPanel getPanel() {
        return panels.get(0);
    }

    public void setPanel(SolarPanel panel) {
        this.panels = new ArrayList<SolarPanel>();
        this.panels.add(panel);
    }

    public List<SolarPanel> getPanels() {
        return panels;
    }

    public void setPanels(List<SolarPanel> panels) {
        this.panels = new ArrayList<SolarPanel>(panels);
    }
}