                        fields = new HashMap<String, String>(fieldsJson.size());
                        for (String f : fieldsJson.getNames()) {
                            fields.put(f, String.valueOf(fieldsJson.get(f)));
                        }
                    } else {
                        fields = Collections.emptyMap();
                    }

                    hits.add(new DefaultSearchQueryRow(index, id, score, explanationJson, locations, fragments, fields));
                }

                List<FacetResult> facets;
                JsonObject facetsJson = json.getObject("facets");
                if (facetsJson != null) {
                    facets = new ArrayList<FacetResult>(facetsJson.size());
                    for (String facetName : facetsJson.getNames()) {
                        JsonObject facetJson = facetsJson.getObject(facetName);
                        String field = facetJson.getString("field");
                        long total = facetJson.getLong("total");
                        long missing = facetJson.getLong("missing");
                        long other = facetJson.getLong("other");

                        if (facetJson.containsKey("numeric_ranges")) {
                            JsonArray rangesJson = facetJson.getArray("numeric_ranges");
                            List<NumericRange> nr = new ArrayList<NumericRange>(rangesJson.size());
                            for (Object o : rangesJson) {
                                JsonObject r = (JsonObject) o;
                                nr.add(new NumericRange(r.getString("name"), r.getDouble("min"), r.getDouble("max"), r.getLong("count")));
                            }
                            facets.add(new DefaultNumericRangeFacetResult(facetName, field, total, missing, other, nr));
                        } else if (facetJson.containsKey("date_ranges")) {
                            JsonArray rangesJson = facetJson.getArray("date_ranges");
                            List<DateRange> dr = new ArrayList<DateRange>(rangesJson.size());
                            for (Object o : rangesJson) {
                                JsonObject r = (JsonObject) o;
                                dr.add(new DateRange(r.getString("name"), r.getString("start"), r.getString("end"), r.getLong("count")));
                            }
                            facets.add(new DefaultDateRangeFacetResult(facetName, field, total, missing, other, dr));
                        } else {
                            JsonArray rangesJson = facetJson.getArray("terms");
                            List<TermRange> tr = new ArrayList<TermRange>(rangesJson.size());
                            for (Object o : rangesJson) {
                                JsonObject r = (JsonObject) o;
                                tr.add(new TermRange(r.getString("term"), r.getLong("count")));
                            }
                            facets.add(new DefaultTermFacetResult(facetName, field, total, missing, other, tr));
                        }
