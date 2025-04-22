    public void testParseBucketsClustered() throws Exception {
	StringBuilder sb = new StringBuilder();
	sb.append(bucketsClusterString);
	sb.append(bucketsClusterString2);
        Map<String, Bucket> buckets = configParser.parseBuckets(sb.toString());
        for (Bucket bucket : buckets.values()) {
            checkBucket(bucket);
        }
    }

