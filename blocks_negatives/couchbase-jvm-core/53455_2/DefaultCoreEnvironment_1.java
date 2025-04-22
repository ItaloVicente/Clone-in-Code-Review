        /**
         * This operation doesn't make sense on the Builder and will throw an {@link UnsupportedOperationException}.
         * @return never.
         * @throws UnsupportedOperationException when invoked.
         */
        @Override
        public Observable<Boolean> shutdown() {
            throw new UnsupportedOperationException("Shutdown should not be called on the Builder.");
        }

        @Override
        public EventLoopGroup ioPool() {
            return ioPool;
        }

