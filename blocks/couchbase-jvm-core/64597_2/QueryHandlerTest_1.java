    @Test
    public void shouldDecodeChunkedResponseSplitAtEveryPositionNoMetrics() throws Throwable {
        String response = Resources.read("chunkedNoMetrics.json", this.getClass());
        for (int i = 1; i < response.length() - 1; i++) {
            String chunk1 = response.substring(0, i);
            String chunk2 = response.substring(i);

            try {
                shouldDecodeChunked(false, chunk1, chunk2);
            } catch (Throwable t) {
                LOGGER.info("Test failed in decoding response with chunk and no metrics at position " + i);
                throw t;
            }
        }
    }

    private void shouldDecodeChunked(boolean metrics, String... chunks) throws Exception {
