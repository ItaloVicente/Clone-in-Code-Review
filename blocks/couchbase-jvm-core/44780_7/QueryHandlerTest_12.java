    private void assertResponse(GenericQueryResponse inbound,
            boolean expectedSuccess, ResponseStatus expectedStatus,
            String expectedRequestId, String expectedClientId,
            String expectedFinalStatus,
            Action1<ByteBuf> assertRows,
            Action1<ByteBuf> assertErrors,
            Map<String, Object> metricsToCheck) {
        assertEquals(expectedSuccess, inbound.status().isSuccess());
        assertEquals(expectedStatus, inbound.status());
        assertEquals(expectedRequestId, inbound.requestId());
        assertEquals(expectedClientId, inbound.clientRequestId());

        assertEquals(expectedFinalStatus, inbound.queryStatus().timeout(1, TimeUnit.SECONDS).toBlocking().single());

        inbound.rows().timeout(5, TimeUnit.SECONDS).toBlocking()
               .forEach(assertRows);

        List<ByteBuf> metricList = inbound.info().timeout(1, TimeUnit.SECONDS).toList().toBlocking().single();
        assertEquals(1, metricList.size());
        String metricsJson = metricList.get(0).toString(CharsetUtil.UTF_8);
        try {
            Map metrics = mapper.readValue(metricsJson, Map.class);
            assertEquals(7, metrics.size());

            for (Map.Entry<String, Object> entry : metricsToCheck.entrySet()) {
                assertNotNull(metrics.get(entry.getKey()));
                assertEquals(entry.getKey(), entry.getValue(), metrics.get(entry.getKey()));
            }
        } catch (IOException e) {
            fail();
        }

        inbound.errors().timeout(1, TimeUnit.SECONDS).toBlocking()
               .forEach(assertErrors);
    }

    private static Map<String, Object> expectedMetricsCounts(int expectedErrors, int expectedResults) {
        Map<String, Object> result = new HashMap<String, Object>(2);
        result.put("errorCount", expectedErrors);
        result.put("resultCount", expectedResults);
        return result;
    }

