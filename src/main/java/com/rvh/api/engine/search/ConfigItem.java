package com.rvh.api.engine.search;

import java.util.List;

public class ConfigItem {

    private String uiName;
    private String name;
    private List<Operator> allowedOperators;
    private List<String> allowedCombinations;
    private boolean sortable;
    private UiControlType uiControlType;

    public ConfigItem() {
    }

    public ConfigItem(String uiName, String name, List<Operator> allowedOperators, boolean sortable, UiControlType uiControlType) {
        this.uiName = uiName;
        this.name = name;
        this.allowedOperators = allowedOperators;
        this.sortable = sortable;
        this.uiControlType = uiControlType;
    }

    public String getName() {
        return name;
    }

    public ConfigItem setName(String name) {
        this.name = name;
        return this;
    }

    public List<Operator> getAllowedOperators() {
        return allowedOperators;
    }

    public ConfigItem setAllowedOperators(List<Operator> allowedOperators) {
        this.allowedOperators = allowedOperators;
        return this;
    }

    public List<String> getAllowedCombinations() {
        return allowedCombinations;
    }

    public ConfigItem setAllowedCombinations(List<String> combineAllowedWith) {
        this.allowedCombinations = combineAllowedWith;
        return this;
    }

    public boolean isSortable() {
        return sortable;
    }

    public ConfigItem setSortable(boolean sortable) {
        this.sortable = sortable;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ConfigItem{");
        sb.append("name='").append(name).append('\'');
        sb.append(", allowedOperators=").append(allowedOperators);
        sb.append(", allowedCombinations=").append(allowedCombinations);
        sb.append(", sortable=").append(sortable);
        sb.append('}');
        return sb.toString();
    }

    public boolean isFilter() {
        return true;
    }

    public UiControlType getUiControlType() {
        return uiControlType;
    }

    public void setUiControlType(UiControlType uiControlType) {
        this.uiControlType = uiControlType;
    }
}
