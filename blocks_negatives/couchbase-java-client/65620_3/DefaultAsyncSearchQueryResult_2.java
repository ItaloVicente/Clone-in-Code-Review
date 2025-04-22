            HitLocations locations = DefaultHitLocations.from(hit.getObject("locations"));

            JsonObject fragmentsJson = hit.getObject("fragments");
            Map<String, List<String>> fragments;
            if (fragmentsJson != null) {
                fragments = new HashMap<String, List<String>>(fragmentsJson.size());
                for (String field : fragmentsJson.getNames()) {
                    List<String> fragment;
                    JsonArray fragmentJson = fragmentsJson.getArray(field);
                    if (fragmentJson != null) {
                        fragment = new ArrayList<String>(fragmentJson.size());
                        for (int i = 0; i < fragmentJson.size(); i++) {
                            fragment.add(fragmentJson.getString(i));
