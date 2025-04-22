    @InterfaceStability.Experimental
    public N1qlParams consistentWith(Bucket bucket, MutatedDocument... mutatedDocuments) {
        if (this.mutatedDocuments == null) {
            this.mutatedDocuments = new HashMap<Bucket, List<MutatedDocument>>();
        }

        List<MutatedDocument> mdocs = this.mutatedDocuments.get(bucket);
        if (mdocs == null) {
            mdocs = new ArrayList<MutatedDocument>();
        }

        mdocs.addAll(Arrays.asList(mutatedDocuments));
        this.mutatedDocuments.put(bucket, mdocs);

        return this;
    }

