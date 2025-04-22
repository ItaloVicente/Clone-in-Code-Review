        JsonArray rawHits = json.getArray("hits");
        if (rawHits != null) {
            for (Object rawHit : rawHits) {
                JsonObject hit = (JsonObject) rawHit;
                String index = hit.getString("index");
                String id = hit.getString("id");
                double score = hit.getDouble("score");
                JsonObject explanationJson = hit.getObject("explanation");
                if (explanationJson == null) {
                    explanationJson = JsonObject.empty();
                }
