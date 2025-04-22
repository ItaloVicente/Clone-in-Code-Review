        if (metricsToCheck == null) {
            assertEquals(0, metricList.size());
        } else {
            assertEquals(1, metricList.size());
            String metricsJson = metricList.get(0).toString(CharsetUtil.UTF_8);
            metricList.get(0).release();
            try {
                Map metrics = mapper.readValue(metricsJson, Map.class);
                assertEquals(7, metrics.size());

                for (Map.Entry<String, Object> entry : metricsToCheck.entrySet()) {
                    assertNotNull(metrics.get(entry.getKey()));
                    assertEquals(entry.getKey(), entry.getValue(), metrics.get(entry.getKey()));
                }
            } catch (IOException e) {
                fail(e.toString());
