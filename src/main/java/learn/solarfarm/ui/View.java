package learn.solarfarm.ui;

import learn.solarfarm.models.SolarPanel;

import java.util.List;

public class View {

    private final TextIO io;

    public View(TextIO io) {
        this.io = io;
    }

    public int chooseMenuOption() {
        displayHeader("Main Menu");
        io.println("0. Exit");
        io.println("1. Find Panels by Section");
//        io.println("2. Add a Panel");
//        io.println("3. Update a Panel");
//        io.println("4. Remove a Panel");
        return io.readInt("Choose [0-1]:", 0, 1);
    }

    public String readString(String prompt) {
        return io.readString(prompt);
    }

//    public SolarPanel choosePanel(List<SolarPanel> panels) {
//        displayPanels(panels);
//        SolarPanel result = null;
//        if (panels.size() > 0) {
//            int memoryId = io.readInt("Choose a Memory ID:");
//            for (Memory m : memories) {
//                if (m.getId() == memoryId) {
//                    result = m;
//                    break;
//                }
//            }
//        }
//        return result;
//    }

//    public boolean isPublic() {
//        io.println("1. Public");
//        io.println("2. Private");
//        return io.readInt("Choose [1-2]:", 1, 2) == 1;
//    }

    public void displayHeader(String message) {
        int length = message.length();
        io.println("");
        io.println(message);
        io.println("=".repeat(length));
    }

    public void displayError(String error) {
        io.println("Error: " + error);
    }

    public void displayPanels(List<SolarPanel> panels) {
        if (panels.size() == 0) {
            displayHeader("No Solar Panels Found.");
        } else {
            displayHeader("Solar Panels:");
            for (SolarPanel p : panels) {
                io.printf("Row: %d, Col: %d, Year: %d, Material: %s, Tracking: %B",
                        p.getRow(), p.getColumn(), p.getYear(), p.getMaterial().toString(), p.isTracking());
            }
        }
    }

    public void displayMessage(String message) {
        io.println("");
        io.println(message);
    }

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
