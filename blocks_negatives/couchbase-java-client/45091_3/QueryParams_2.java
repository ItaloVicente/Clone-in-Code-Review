    public QueryParams consistencyAtPlus(int[] scanVector) {
        if (scanVector.length != 1024) {
            throw new IllegalArgumentException("Full Scan Vector must contain seqno for all 1024 vbuckets");
        }
        this.consistency = ScanConsistency.AT_PLUS;
        List<Integer> fullVector = new ArrayList<Integer>(scanVector.length);
        for (int i : scanVector) {
            fullVector.add(i);
