          public void gotData(String key, long retCas, MemcachedNode node,
              ObserveResponse or) {
            if (cas != retCas) {
              response.put(node, ObserveResponse.MODIFIED);
            } else {
              response.put(node, or);
