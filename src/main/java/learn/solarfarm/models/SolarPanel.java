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

    // Constructor for creating a new solar panel
    public SolarPanel(String section, int row,
                      int column, int year, Material material, boolean isTracking) {
        this.id = UUID.randomUUID(); // generate ID
        this.section = section;
        this.row = row;
        this.column = column;
        this.year = year;
        this.material = material;
        this.isTracking = isTracking;
    }

    // Constructor for creating an object of an existing solar panel (that already has an ID)
    public SolarPanel(UUID id, String section, int row,
                      int column, int year, Material material, boolean isTracking) {
        this.id = id;
        this.section = section;
        this.row = row;
        this.column = column;
        this.year = year;
        this.material = material;
        this.isTracking = isTracking;
    }

    public SolarPanel() {

    }

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

    public Material getMaterial() { return material; }

    public void setMaterial(Material material) { this.material = material; }

    public void setSection(String section) {
        this.section = section;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

}
