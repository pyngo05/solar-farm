package learn.solarfarm.ui;

import learn.solarfarm.models.SolarPanel;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class View {

    private final Scanner console = new Scanner(System.in);

    public MenuOption displayMenuAndSelect() {
        MenuOption[] values = MenuOption.values();
        printHeader("Main Menu");
        for (int i = 0; i < MenuOption.values().length; i++) {
            System.out.printf("%s. %s%n", i, values[i].getTitle());
        }
        int index = readInt("Select [0-4]: ", 0, 4);
        return values[index];
    }

    public void printHeader(String message) {
        System.out.println();
        System.out.println(message);
        System.out.println("=".repeat(message.length()));
    }

    public void printSolarPanels(List<SolarPanel> panels) {
        if (panels == null || panels.size() == 0) {
            System.out.println("No panels.");
            return;
        }

        System.out.println("Row\t Col Year\t Material\t Tracking");
        for (SolarPanel panel : panels) {
            System.out.printf("%d\t %d\t %d\t %s\t\t %B%n",
                    panel.getRow(),
                    panel.getColumn(),
                    panel.getYear(),
                    panel.getMaterial().name(),
                    panel.isTracking()
            );
        }
    }

    public String readString(String prompt) {
        System.out.print(prompt);
        return console.nextLine();
    }

    private String readRequiredString(String prompt) {
        String result = null;
        do {
            result = readString(prompt).trim();
            if (result.length() == 0) {
                System.out.println("Value is required.");
            }
        } while (result.length() == 0);
        return result;
    }

    private int readInt(String prompt) {
        int result = 0;
        boolean isValid = false;
        do {
            String value = readRequiredString(prompt);
            try {
                result = Integer.parseInt(value);
                isValid = true;
            } catch (NumberFormatException ex) {
                System.out.print("Value must be a number.");
            }
        } while (!isValid);
        return result;
    }

    private int readInt(String prompt, int min, int max) {
        int result = 0;
        do {
            result = readInt(prompt);
            if (result < min || result > max) {
                System.out.printf("Value must be between %s and %s.%n", min, max);
            }
        } while (result < min || result > max);

        return result;
    }

    public void displayError(String error) {
        System.out.println("Error: " + error);
    }

//    private final TextIO io;
//
//    public View(TextIO io) {
//        this.io = io;
//    }
//
//    public int chooseMenuOption() {
//        displayHeader("Main Menu");
//        io.println("0. Exit");
//        io.println("1. Find Panels by Section");
////        io.println("2. Add a Panel");
////        io.println("3. Update a Panel");
////        io.println("4. Remove a Panel");
//        return io.readInt("Choose [0-1]:", 0, 1);
//    }
//
//    public String readString(String prompt) {
//        return io.readString(prompt);
//    }
//
////    public SolarPanel choosePanel(List<SolarPanel> panels) {
////        displayPanels(panels);
////        SolarPanel result = null;
////        if (panels.size() > 0) {
////            int memoryId = io.readInt("Choose a Memory ID:");
////            for (Memory m : memories) {
////                if (m.getId() == memoryId) {
////                    result = m;
////                    break;
////                }
////            }
////        }
////        return result;
////    }
//
////    public boolean isPublic() {
////        io.println("1. Public");
////        io.println("2. Private");
////        return io.readInt("Choose [1-2]:", 1, 2) == 1;
////    }
//
//    public void displayHeader(String message) {
//        int length = message.length();
//        io.println("");
//        io.println(message);
//        io.println("=".repeat(length));
//    }
//
//    public void displayError(String error) {
//        io.println("Error: " + error);
//    }
//
//    public void displayPanels(List<SolarPanel> panels) {
//        if (panels.size() == 0) {
//            displayHeader("No Solar Panels Found.");
//        } else {
//            displayHeader("Solar Panels:");
//            for (SolarPanel p : panels) {
//                io.printf("Row: %d, Col: %d, Year: %d, Material: %s, Tracking: %B",
//                        p.getRow(), p.getColumn(), p.getYear(), p.getMaterial().toString(), p.isTracking());
//            }
//        }
//    }
//
//    public void displayMessage(String message) {
//        io.println("");
//        io.println(message);
//    }

//    public SolarPanel createPanel() {
//        displayHeader("Add a Panel");
//        SolarPanel result = new SolarPanel();
//        result.setSection(io.readString("From: "));
//        result.setContent(io.readString("Content: "));
//        result.setShareable(io.readBoolean("Shareable [y/n]: "));
//        return result;
//    }

//    public Memory editMemory(Memory m) {
//        displayHeader("Update");
//
//        String from = io.readString("From (" + m.getFrom() + "): ");
//        // only update if it changed
//        if (from.trim().length() > 0) {
//            m.setFrom(from);
//        }
//
//        String content = io.readString("Content (" + m.getContent() + "): ");
//        if (content.trim().length() > 0) {
//            m.setContent(content);
//        }
//
//        String shareable = io.readString("Shareable (" + (m.isShareable() ? "y" : "n") + ") [y/n]: ");
//        if (shareable.trim().length() > 0) {
//            m.setShareable(shareable.equalsIgnoreCase("y"));
//        }
//        return m;
//    }
}
