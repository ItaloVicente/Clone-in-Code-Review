        }
        if (consistency != null || mutationState != null) {
            JsonObject consistencyJson = JsonObject.create();

            if (consistency == ScanConsistency.NOT_BOUNDED) {
                consistencyJson.put("level", "");
            } else if (mutationState != null) {
                consistencyJson.put("level", "at_plus");
                consistencyJson.put("vectors", JsonObject.create().put(this.indexName, mutationState.exportForFts()));
            }
            control.put("consistency", consistencyJson);
        }
        if (!control.isEmpty()) {
