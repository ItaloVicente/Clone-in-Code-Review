		private static volatile SshSessionFactory INSTANCE = loadSshSessionFactory();

		private static SshSessionFactory loadSshSessionFactory() {
			ServiceLoader<SshSessionFactory> loader = ServiceLoader
					.load(SshSessionFactory.class);
			Iterator<SshSessionFactory> iter = loader.iterator();
			if (iter.hasNext()) {
				return iter.next();
			}
			return null;
		}

		private DefaultFactory() {
		}

		public static SshSessionFactory getInstance() {
			return INSTANCE;
		}

		public static void setInstance(SshSessionFactory newFactory) {
			if (newFactory != null) {
				INSTANCE = newFactory;
			} else {
				INSTANCE = loadSshSessionFactory();
			}
