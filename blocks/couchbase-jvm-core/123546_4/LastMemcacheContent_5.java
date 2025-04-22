
        @Override
        public LastMemcacheContent retainedDuplicate() {
            return this;
        }

        @Override
        public LastMemcacheContent replace(ByteBuf content) {
            throw new UnsupportedOperationException("replace");
        }

        @Override
        public LastMemcacheContent touch() {
            return this;
        }

        @Override
        public LastMemcacheContent touch(Object hint) {
            return this;
        }
