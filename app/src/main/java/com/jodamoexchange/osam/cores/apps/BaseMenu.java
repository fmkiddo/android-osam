package com.jodamoexchange.osam.cores.apps;

import java.util.Objects;

public abstract class BaseMenu implements AppMenu {

    private int resId;
    private String defaultString;
    private AppFragment associatedFragment;

    protected BaseMenu (int resId, String defaultString, AppFragment associatedFragment) {
        this.resId = resId;
        this.defaultString = defaultString;
        this.associatedFragment = associatedFragment;
    }

    protected BaseMenu (int resId, String defaultString) {
        this (resId, defaultString, null);
    }

    @Override
    public int getResourceId() {
        return this.resId;
    }

    @Override
    public AppFragment getFragment() {
        return this.associatedFragment;
    }

    @Override
    public String getDefaultString() {
        return this.defaultString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseMenu baseMenu = (BaseMenu) o;
        return resId == baseMenu.resId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(resId);
    }
}
