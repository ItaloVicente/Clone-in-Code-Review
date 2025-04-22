        @InterfaceAudience.Public
        @InterfaceStability.Uncommitted
        public SELF networkResolution(final NetworkResolution networkResolution) {
            if (networkResolution == null) {
                throw new IllegalArgumentException("NetworkResolution must not be null!");
            }
            this.networkResolution = networkResolution;
            return self();
        }

