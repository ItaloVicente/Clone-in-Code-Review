        public Builder tracer(final Tracer tracer) {
            if (tracer == null) {
                throw new IllegalArgumentException("Tracer cannot be null.");
            }
            this.tracer = tracer;
            return this;
        }

