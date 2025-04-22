        @InterfaceAudience.Public
        @InterfaceStability.Uncommitted
        public SELF minCompressionSize(final int minCompressionSize) {
            this.minCompressionSize = minCompressionSize;
            return self();
        }

        @InterfaceAudience.Public
        @InterfaceStability.Uncommitted
        public SELF minCompressionRatio(final double minCompressionRatio) {
            this.minCompressionRatio = minCompressionRatio;
            return self();
        }

