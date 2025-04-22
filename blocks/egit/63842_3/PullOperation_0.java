
	public static class PullReferenceConfig {
		private String remote;

		private String reference;

		private UpstreamConfig upstreamConfig;

		public PullReferenceConfig(@Nullable String remote,
				@Nullable String reference,
				@Nullable UpstreamConfig upstreamConfig) {
			this.remote = remote;
			this.reference = reference;
			this.upstreamConfig = upstreamConfig;
		}

		@Nullable
		public String getRemote() {
			return this.remote;
		}

		@Nullable
		public String getReference() {
			return this.reference;
		}

		@Nullable
		public UpstreamConfig getUpstreamConfig() {
			return this.upstreamConfig;
		}
	}

