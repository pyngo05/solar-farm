package learn.solarfarm.domain;
import learn.solarfarm.models.SolarPanel;
import java.util.ArrayList;
import java.util.List;

// Return type for domain methods that may fail.
// If a method fails, the result contains error messages that explain the failure.
public class SolarPanelResult {

        private ArrayList<String> messages = new ArrayList<>();
        private SolarPanel panel;

        public List<String> getErrorMessages() {
            return new ArrayList<>(messages);
        }

        public void addErrorMessage(String message) {
            messages.add(message);
        }

        public boolean isSuccess() {
            // If an error message exists, the operation failed.
            return messages.size() == 0;
        }

        public SolarPanel getPanel() {
            return panel;
        }

        public void setPanel(SolarPanel panel) {
            this.panel = panel;
        }
}