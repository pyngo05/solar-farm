package learn.solarfarm.ui;

import learn.solarfarm.data.XDataAccessException;
import learn.solarfarm.domain.SolarPanelResult;
import learn.solarfarm.domain.SolarPanelService;
import learn.solarfarm.models.SolarPanel;

import java.awt.*;
import java.util.List;

public class Controller {

    private final SolarPanelService service;
    private final View view;

    public Controller(SolarPanelService service, View view) {
        this.service = service;
        this.view = view;
    }

    public void run() {
        MenuOption option;
        do {
            option = view.displayMenuAndSelect();
            System.out.println(option.getTitle());
            try {
                switch (option) {
                    case EXIT:
                        view.printHeader("Goodbye!");
                        break;
                    case DISPLAY_PANELS:
                        displayPanels();
                        break;
                    case CREATE_PANELS:
                        createPanel();
                        break;
                    case UPDATE_PANELS:
                        updatePanel();
                        break;
                    case DELETE_PANELS:
                        deletePanel();
                        break;
                }
            }
            catch (XDataAccessException ex) {
                view.displayError("Data access error occurred.");
            }
            catch (Exception ex) {
                view.displayError("Unknown error occurred.");
            }
        } while (option != MenuOption.EXIT);
    }

    private void displayPanels() throws XDataAccessException {
        view.printHeader(MenuOption.DISPLAY_PANELS.getTitle());

        // get section from user
        String section = view.readString("Section Name:");
        if (section.isEmpty()) {
            view.displayError("Section name must not be empty.");
            return;
        }

        // find matching panels
        SolarPanelResult result = service.findBySection(section);
        if (!result.isSuccess()) {
            view.displayError(result.getErrorMessage());
            return;
        }

        // display panels
        view.printSolarPanels(result.getPanels());
    }

    private void createPanel() {
        view.printHeader(MenuOption.CREATE_PANELS.getTitle());
    }

    private void updatePanel() {
        view.printHeader(MenuOption.UPDATE_PANELS.getTitle());
    }

    private void deletePanel() {
        view.printHeader(MenuOption.DELETE_PANELS.getTitle());
    }

//    public void run() {
//        view.displayHeader("Welcome to Solar Farm.");
//        try {
//            runApp();
//        } catch (XDataAccessException ex) {
//            view.displayError(ex.getMessage());
//        }
//        view.displayMessage("Goodbye.");
//    }
//
//    private void runApp() throws XDataAccessException {
//
//        for (int option = view.chooseMenuOption();
//             option > 0;
//             option = view.chooseMenuOption()) {
//
//            switch (option) {
//                case 1:
//                    findPanelsBySection();
//                    break;
////                    case 2:
////                        addMemory();
////                        break;
////                    case 3:
////                        updateMemory();
////                        break;
////                    case 4:
////                        deleteMemory();
////                        break;
//                default:
//                    view.displayError("Invalid option.");
//            }
//        }
//    }
//
//    private void findPanelsBySection() throws XDataAccessException {
//        String section = view.readString("Section Name:");
//
//        SolarPanelResult result = service.findBySection(section);
//        if (!result.isSuccess()) {
//            view.displayError(result.getErrorMessage());
//        }
//
//        view.displayPanels(result.getPanels());
//    }

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