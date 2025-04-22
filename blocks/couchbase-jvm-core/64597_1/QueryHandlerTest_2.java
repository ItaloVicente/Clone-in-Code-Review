        Map<String, Object> expectedMetrics;
        if (metrics) {
            expectedMetrics = expectedMetricsCounts(5678, 1234); //these are the numbers parsed from metrics object, not real count
        } else {
            expectedMetrics = null;
        }
