        @Override
        public BucketStreamState next() {
            if (hasNext()) {
                return feeds[index++];
            } else {
                throw new NoSuchElementException("There are no elements. size = " + feeds.length);
            }
        }
