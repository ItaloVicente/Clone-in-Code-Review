
	public static class Options {

		private boolean bare;

		private boolean autoClose;

		private boolean useRefCache;

		public Options setBare(boolean bare) {
			this.bare = bare;
			return this;
		}

		public Options setAutoClose(boolean autoClose) {
			this.autoClose = autoClose;
			return this;
		}


		public Options setUseRefCache(boolean useRefCache) {
			this.useRefCache = useRefCache;
			return this;
		}

		public boolean bare() {
			return bare;
		}

		public boolean autoClose() {
			return autoClose;
		}

		public boolean useRefCache() {
			return useRefCache;
		}
	}

