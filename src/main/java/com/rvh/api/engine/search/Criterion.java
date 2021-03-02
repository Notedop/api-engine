package com.rvh.api.engine.search;

public class Criterion {

    String name;
    String searchValue;
    Operator operator;
    boolean sortAsc;

    public Criterion(String name, String searchValue, Operator operator, boolean sortAsc) {
        this.name = name;
        this.searchValue = searchValue;
        this.operator = operator;
        this.sortAsc = sortAsc;
    }

    public String getName() {
        return name;
    }

    public Criterion setName(String name) {
        this.name = name;
        return this;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public Criterion setSearchValue(String searchValue) {
        this.searchValue = searchValue;
        return this;
    }

    public Operator getOperator() {
        return operator;
    }

    public Criterion setOperator(Operator operator) {
        this.operator = operator;
        return this;
    }

    public boolean getSortAsc() {
        return sortAsc;
    }

    public Criterion setSortAsc(boolean sortAsc) {
        this.sortAsc = sortAsc;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Criterium{");
        sb.append("name='").append(name).append('\'');
        sb.append(", searchValue='").append(searchValue).append('\'');
        sb.append(", operator=").append(operator);
        sb.append(", sortAsc=").append(sortAsc);
        sb.append('}');
        return sb.toString();
    }
}
