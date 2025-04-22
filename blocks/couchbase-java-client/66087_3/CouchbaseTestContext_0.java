        public Builder sampleBucket(String sampleName) {
            bucketName(sampleName);
            bucketPassword("");
            flushOnInit(false);
            adhoc(false);
            createBucketIfMissing(false);
            return this;
        }

