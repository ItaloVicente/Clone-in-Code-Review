        public Builder bucketSample(String sampleName) {
            bucketName(sampleName);
            bucketPassword("");
            flushOnInit(false);
            adhoc(false);
            createIfMissing(false);
            return this;
        }

