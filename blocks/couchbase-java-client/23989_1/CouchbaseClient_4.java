        @Override
        public boolean isCancelled() {
          throw new UnsupportedOperationException("Flush cannot be"
            + " canceled.");
        }
      };
