    @Test
    public void shouldLogInDescendingOrder() {
        TestReporter reporter = null;
        try {
            reporter = new TestReporter(new ThresholdLogReporter.Builder()
                .kvThreshold(1, TimeUnit.MILLISECONDS)
                .logInterval(1, TimeUnit.SECONDS)
            );

            List<Long> allDurations = new ArrayList<Long>();
            int numRequests = 100;
            for (int i = 0; i < numRequests; i++) {
                CouchbaseRequest request = mock(CouchbaseRequest.class);
                when(request.operationId()).thenReturn("0x" + i);
                ThresholdLogSpan span = mock(ThresholdLogSpan.class);
                when(span.compareTo(any(ThresholdLogSpan.class))).thenCallRealMethod();
                when(span.tag("peer.service")).thenReturn("kv");
                when(span.operationName()).thenReturn("get");
                long duration = TimeUnit.SECONDS.toMicros(new Random().nextInt(10));
                when(span.durationMicros()).thenReturn(duration);
                allDurations.add(duration);
                when(span.request()).thenReturn(request);

                reporter.report(span);
            }

            reporter.waitUntilOverThreshold(1);

            List<Long> totalDurations = new ArrayList<Long>();
            for (List<Map<String, Object>> allLogEntries : reporter.overThreshold()) {
                for (Map<String, Object> logEntry : allLogEntries) {
                    List<Map<String, Object>> topEntries = (List<Map<String, Object>>) logEntry.get("top");
                    for (Map<String, Object> entry : topEntries) {
                        totalDurations.add((Long) entry.get("total_us"));
                    }
                }
            }

            Collections.sort(allDurations, Collections.<Long>reverseOrder());
            List<Long> sortedDescending = allDurations.subList(0, 10);
            assertEquals(sortedDescending, totalDurations);
        } finally {
            if (reporter != null) {
                reporter.shutdown();
            }
        }
    }

