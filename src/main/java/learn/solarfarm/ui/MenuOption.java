package learn.solarfarm.ui;

public enum MenuOption {

    EXIT("Exit"),
    DISPLAY_PANELS("Display Panels"),
    CREATE_PANELS("Create Panel"),
    UPDATE_PANELS("Update Panel"),
    DELETE_PANELS("Delete Panel");

    private final String title;

    MenuOption(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }


}