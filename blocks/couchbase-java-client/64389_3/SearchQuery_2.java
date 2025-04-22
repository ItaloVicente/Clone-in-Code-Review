    public JsonObject export() {
        JsonObject result = JsonObject.create();
        injectParams(result);

        JsonObject queryJson = JsonObject.create();
        queryPart.injectParamsAndBoost(queryJson);
        return result.put("query", queryJson);
    }

    public void injectParams(JsonObject queryJson) {
        if (limit != null && limit >= 0) {
            queryJson.put("size", limit);
        }
        if (skip != null && skip >= 0) {
            queryJson.put("from", skip);
        }
        if (explain != null) {
            queryJson.put("explain", explain);
        }
        if (highlightStyle != null) {
            JsonObject highlight = JsonObject.create();
            highlight.put("style", highlightStyle.name().toLowerCase());
            if (highlightFields != null && highlightFields.length > 0) {
                highlight.put("fields", JsonArray.from(highlightFields));
            }
            queryJson.put("highlight", highlight);
        }
        if (fields != null && fields.length > 0) {
            queryJson.put("fields", JsonArray.from(fields));
        }
        if (!this.facets.isEmpty()) {
            JsonObject facets = JsonObject.create();
            for (SearchFacet f : this.facets.values()) {
                JsonObject facet = JsonObject.create();
                f.injectParams(facet);
                facets.put(f.name(), facet);
            }
            queryJson.put("facets", facets);
        }
        if(serverSideTimeout != null) {
            JsonObject control = JsonObject.empty();
            control.put("timeout", serverSideTimeout);
            queryJson.put("ctl", control);
        }
    }



    public SearchQuery limit(int limit) {
        this.limit = limit;
        return this;
    }

    public SearchQuery skip(int skip) {
        this.skip = skip;
        return this;
    }

    public SearchQuery explain() {
        return explain(true);
    }

    public SearchQuery explain(boolean explain) {
        this.explain = explain;
        return this;
    }

    public SearchQuery highlight(HighlightStyle style, String... fields) {
        this.highlightStyle = style;
        if (fields != null && fields.length > 0) {
            highlightFields = fields;
        }
        return this;
    }

    public SearchQuery clearHighlight() {
        this.highlightStyle = null;
        this.highlightFields = null;
        return this;
    }

    public SearchQuery fields(String... fields) {
        if (fields != null) {
            this.fields = fields;
        }
        return this;
    }

    public SearchQuery addFacets(SearchFacet... facets) {
        if (facets != null) {
            for (SearchFacet facet : facets) {
                this.facets.put(facet.name(), facet);
            }
        }
        return this;
    }

    public SearchQuery clearFacets() {
        this.facets.clear();
        return this;
    }

    public SearchQuery serverSideTimeout(long timeout, TimeUnit unit) {
        this.serverSideTimeout = unit.toMillis(timeout);
        return this;
    }

    public Integer getLimit() {
        return limit;
    }

    public Integer getSkip() {
        return skip;
    }

    public HighlightStyle getHighlightStyle() {
        return highlightStyle;
    }

    public String[] getHighlightFields() {
        return highlightFields;
    }

    public String[] getFields() {
        return fields;
    }

    public Map<String, SearchFacet> getFacets() {
        return facets;
    }

