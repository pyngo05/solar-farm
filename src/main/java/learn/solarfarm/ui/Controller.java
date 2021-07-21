package learn.solarfarm.ui;

import learn.solarfarm.data.XDataAccessException;
import learn.solarfarm.domain.SolarPanelResult;
import learn.solarfarm.domain.SolarPanelService;
import learn.solarfarm.models.Material;
import learn.solarfarm.models.SolarPanel;

import java.awt.*;
import java.util.List;
import java.util.Locale;

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

    private void createPanel() throws XDataAccessException {
        view.printHeader(MenuOption.CREATE_PANELS.getTitle());
        SolarPanel panel = view.createPanel();
        SolarPanelResult result = service.add(panel);
        view.displayResult(result);
    }

    private void updatePanel() throws XDataAccessException {
        view.printHeader(MenuOption.UPDATE_PANELS.getTitle());

        // get fields of panel to update
        String section = view.readString("Section: ");
        int r = view.readInt("Row: ");
        int c = view.readInt("Column: ");

        // find panel
        SolarPanelResult result = service.findPanelBySectionRowColumn(section, r, c);
        if (!result.isSuccess()) {
            view.displayError(result.getErrorMessage());
            return;
        }
        SolarPanel panel = result.getPanel();

        // get updated fields (ints are String to allow user to give empty)
        System.out.println("\nProvide new values (empty to not update that field):");
        section = view.readString("Section: ");
        String row = view.readString("Row: ");
        String column = view.readString("Column: ");
        String material = view.readString("Material: ");
        String year = view.readString("Year: ");
        String tracking = view.readString("Tracked [y/n]: ");

        // update panel (only fields that user has provided; keep others the same)
        if (!section.isEmpty()) {
            panel.setSection(section);
        }
        if (!row.isEmpty()) {
            panel.setRow(Integer.parseInt(row));
        }
        if (!column.isEmpty()) {
            panel.setColumn(Integer.parseInt(column));
        }
        if (!material.isEmpty()) {
            panel.setMaterial(Material.valueOf(material));
        }
        if (!year.isEmpty()) {
            panel.setYear(Integer.parseInt(year));
        }
        if (!tracking.isEmpty()) {
            panel.setTracking(tracking.toLowerCase().equals("y"));
        }
        result = service.update(panel.getId(), panel);
        if (!result.isSuccess()) {
            view.displayError(result.getErrorMessage());
            return;
        }

        System.out.println("\nPanel updated.");
    }

    private void deletePanel() throws XDataAccessException {
        view.printHeader(MenuOption.DELETE_PANELS.getTitle());

        int id = view.readInt("Panel ID: ");

        SolarPanelResult result = service.deleteById(id);
        if (!result.isSuccess()) {
            view.displayError(result.getErrorMessage());
            return;
        }

        System.out.println("\nPanel deleted.");
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