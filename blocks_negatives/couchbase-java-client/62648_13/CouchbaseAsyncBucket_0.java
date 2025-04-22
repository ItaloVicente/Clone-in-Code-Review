                    if (explanationJson != null) {
                        explanation = explanationJson.toString();
                    }
                    Map<String, Map<String, List<SearchQueryRow.Location>>> locations = null;
                    JsonObject locationsJson = hit.getObject("locations");
                    if (locationsJson != null) {
                        locations = new HashMap<String, Map<String, List<SearchQueryRow.Location>>>();
                        for (String field : locationsJson.getNames()) {
                            JsonObject termsJson = locationsJson.getObject(field);
                            Map<String, List<SearchQueryRow.Location>> terms = new HashMap<String, List<SearchQueryRow.Location>>();
                            for (String term : termsJson.getNames()) {
                                JsonArray locsJson = termsJson.getArray(term);
                                List<SearchQueryRow.Location> locs = new ArrayList<SearchQueryRow.Location>(locsJson.size());
                                for (int i = 0; i < locsJson.size(); i++) {
                                    JsonObject loc = locsJson.getObject(i);
                                    long pos = loc.getLong("pos");
                                    long start = loc.getLong("start");
                                    long end = loc.getLong("end");
                                    JsonArray arrayPositionsJson = loc.getArray("array_positions");
                                    long[] arrayPositions = null;
                                    if (arrayPositionsJson != null) {
                                        arrayPositions = new long[arrayPositionsJson.size()];
                                        for (int j = 0; j < arrayPositionsJson.size(); j++) {
                                            arrayPositions[j] = arrayPositionsJson.getLong(j);
                                        }
                                    }
                                    locs.add(new SearchQueryRow.Location(pos, start, end, arrayPositions));
                                }
                                terms.put(term, locs);
                            }
                            locations.put(field, terms);
                        }
