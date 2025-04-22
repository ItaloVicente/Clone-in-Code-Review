    /**
     * Export the FTS query in its JSON format, not taking any global level {@link SearchParams parameters} into account.
     *
     * @return the FTS query JSON.
     */
    public JsonObject export() {
        return export(null);
    }

    /**
     * Export the FTS query in its JSON format, taking global level {@link SearchParams parameters} into account.
     *
     * @param searchParams the global level parameters for the search.
     * @return the FTS query JSON.
     */
    public JsonObject export(SearchParams searchParams) {
        JsonObject result = JsonObject.create();
        if (searchParams != null) {
            searchParams.injectParams(result);
        }
        JsonObject query = JsonObject.create();
        injectParamsAndBoost(query);
        return result.put("query", query);
    }

