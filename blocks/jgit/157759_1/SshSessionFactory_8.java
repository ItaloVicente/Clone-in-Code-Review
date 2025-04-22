	private static SshSessionFactory loadSshSessionFactory() {
		ServiceLoader<SshSessionFactory> loader = ServiceLoader.load(SshSessionFactory.class);
		Iterator<SshSessionFactory> iter = loader.iterator();
		if(iter.hasNext()) {
			return iter.next();
		}
		return null;
	}
