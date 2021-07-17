package learn.solarfarm.models;

import java.util.UUID;

public class SolarPanel {

    // Uniquely identify a panel
    private UUID id;
    private String section;
    private int row;
    private int column;
    private int year;
    private Material material;
    private boolean isTracking;

    // Constructor for creating a solar panel
    public SolarPanel(String section, int row,
                      int column, int year, Material material, boolean isTracking) {
        this.id = UUID.randomUUID();
        this.section = section;
        this.row = row;
        this.column = column;
        this.year = year;
        this.material = material;
        this.isTracking = isTracking;
    }

    // All fields have getters and setters
    public UUID getId() {
        return id;
    }

    public String getSection() {
        return section;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isTracking() {
        return isTracking;
    }

    public void setTracking(boolean tracking) {
        isTracking = tracking;
    }

    // TODO getter and setter for material

}
