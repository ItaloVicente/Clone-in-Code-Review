    @Override
    public Observable<SearchQueryResult> query(final SearchQuery query) {
        Observable<SearchQueryResponse> source = Observable.defer(new Func0<Observable<SearchQueryResponse>>() {
            @Override
            public Observable<SearchQueryResponse> call() {
                final SearchQueryRequest request =
                    new SearchQueryRequest(query.index(), query.json().toString(), bucket, password);
                return core.send(request);
            }
        });

        return source.map(new Func1<SearchQueryResponse, SearchQueryResult>() {
            @Override
            public SearchQueryResult call(SearchQueryResponse response) {
                if (!response.status().isSuccess()) {
                    throw new CouchbaseException("Could not query search index: " + response.payload());
                }

                JsonObject json = JsonObject.fromJson(response.payload());
                long totalHits = json.getLong("total_hits");
                long took = json.getLong("took");
                double maxScore = json.getDouble("max_score");
                List<SearchQueryRow> hits = new ArrayList<SearchQueryRow>();
                for (Object rawHit : json.getArray("hits")) {
                    JsonObject hit = (JsonObject)rawHit;
                    String index = hit.getString("index");
                    String id = hit.getString("id");
                    double score = hit.getDouble("score");
                    String explanation = null;
                    JsonObject explanationJson = hit.getObject("explanation");
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
                    }

                    Map<String, String[]> fragments = null;
                    JsonObject fragmentsJson = hit.getObject("fragments");
                    if (fragmentsJson != null) {
                        fragments = new HashMap<String, String[]>();
                        for (String field : fragmentsJson.getNames()) {
                            JsonArray fragmentJson = fragmentsJson.getArray(field);
                            String[] fragment = null;
                            if (fragmentJson != null) {
                                fragment = new String[fragmentJson.size()];
                                for (int i = 0; i < fragmentJson.size(); i++) {
                                    fragment[i] = fragmentJson.getString(i);
                                }
                            }
                            fragments.put(field, fragment);
                        }
                    }

                    Map<String, Object> fields = null;
                    JsonObject fieldsJson = hit.getObject("fields");
                    if (fieldsJson != null) {
                        fields = fieldsJson.toMap();
                    }

                    hits.add(new SearchQueryRow(index, id, score, explanation, locations, fragments, fields));
                }
                return new SearchQueryResult(took, totalHits, maxScore, hits);
            }
        });
    }

