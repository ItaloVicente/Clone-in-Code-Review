    public static DateRangeFacet date(String name, String field, int limit) {
        return new DateRangeFacet(name, field, limit);
    }

    public String name() {
        return name;
