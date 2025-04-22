
    static class BucketCacheKey {
        private final String bucketName;
        private final List<Transcoder> transcoders;

        BucketCacheKey(String bucketName, List<Transcoder<? extends Document, ?>> transcoders) {
            if (bucketName == null) {
                throw new NullPointerException("Bucket name must be non-null");
            }
            this.bucketName = bucketName;

            if (transcoders == null || transcoders.isEmpty()) {
                this.transcoders = Collections.emptyList();
            } else {
                this.transcoders = Collections.unmodifiableList(new ArrayList<Transcoder>(transcoders));
            }
        }

        String bucketName() {
            return bucketName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            BucketCacheKey that = (BucketCacheKey) o;

            if (!bucketName.equals(that.bucketName)) return false;
            return transcoders.equals(that.transcoders);
        }

        @Override
        public int hashCode() {
            int result = bucketName.hashCode();
            result = 31 * result + transcoders.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return "BucketCacheKey{" +
                    "bucketName='" + bucketName + '\'' +
                    ", transcoders=" + transcoders +
                    '}';
        }
    }
