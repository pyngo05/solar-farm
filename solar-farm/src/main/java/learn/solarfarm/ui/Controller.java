package learn.solarfarm.ui;

import learn.solarfarm.data.XDataAccessException;
import learn.solarfarm.domain.SolarPanelResult;
import learn.solarfarm.domain.SolarPanelService;
import learn.solarfarm.models.SolarPanel;

import java.util.List;

public class Controller {

        private final View view;
        private final SolarPanelService service;

        public Controller(View view, SolarPanelService service) {
            this.view = view;
            this.service = service;
        }

        public void run() {
            view.displayHeader("Welcome to Solar Farm.");
            try {
                runApp();
            } catch (XDataAccessException ex) {
                view.displayError(ex.getMessage());
            }
            view.displayMessage("Goodbye.");
        }

        private void runApp() throws XDataAccessException {

            for (int option = view.chooseMenuOption();
                 option > 0;
                 option = view.chooseMenuOption()) {

                switch (option) {
                    case 1:
                        findPanelsBySection();
                        break;
//                    case 2:
//                        addMemory();
//                        break;
//                    case 3:
//                        updateMemory();
//                        break;
//                    case 4:
//                        deleteMemory();
//                        break;
                    default:
                        view.displayError("Invalid option.");
                }
            }
        }

        private void findPanelsBySection() throws XDataAccessException {
            String section = view.readString("Section Name:");

            SolarPanelResult result = service.findBySection(section);
            if (!result.isSuccess()) {
                view.displayError(result.getErrorMessage());
            }

            view.displayPanels(result.getPanels());
        }

//        private void addMemory() throws XDataAccessException {
//            Memory m = view.createMemory();
//            MemoryResult result = service.add(m);
//            if (result.isSuccess()) {
//                view.displayMessage("Memory " + result.getMemory().getId() + " created.");
//            } else {
//                view.displayErrors(result.getErrorMessages());
//            }
//        }
//
//        private void updateMemory() throws XDataAccessException {
//            List<Memory> memories = getMemories("Update a Memory");
//            Memory m = view.chooseMemory(memories);
//            if (m == null) {
//                view.displayMessage("Memory not found.");
//                return;
//            }
//            m = view.editMemory(m);
//            MemoryResult result = service.update(m);
//            if (result.isSuccess()) {
//                view.displayMessage("Memory " + result.getMemory().getId() + " updated.");
//            } else {
//                view.displayErrors(result.getErrorMessages());
//            }
//        }
//
//        private void deleteMemory() throws XDataAccessException {
//            List<Memory> memories = getMemories("Delete a Memory");
//            Memory m = view.chooseMemory(memories);
//            if (m != null && service.deleteById(m.getId()).isSuccess()) {
//                view.displayMessage("Memory " + m.getId() + " deleted.");
//            } else {
//                view.displayMessage("Memory not found.");
//            }
//        }
//
//        private List<Memory> getMemories(String title) throws XDataAccessException {
//            view.displayHeader(title);
//            if (view.isPublic()) {
//                return service.findPublicMemories();
//            }
//            return service.findPrivateMemories();
//
//        }
}