package me.fluxcapacitor.dragonprotect;

public class Region {

    private Coordinate bound1;
    private Coordinate bound2;
    private String permission;

    public Region(Coordinate bound1, Coordinate bound2, String permission) {
        this.bound1 = bound1;
        this.bound2 = bound2;
        this.permission = permission;
    }

    public Coordinate getBound1() {
        return this.bound1;
    }

    public void setBound1(Coordinate newBound) {
        this.bound1 = newBound;
    }

    public Coordinate getBound2() {
        return this.bound2;
    }

    public void setBound2(Coordinate newBound) {
        this.bound2 = newBound;
    }

    public String getPermission() {
        return this.permission;
    }

    public void setPermission(String newPermission) {
        this.permission = newPermission;
    }
}
