	private static class DummyFamilyJob extends DummyJob {

		public DummyFamilyJob(String name, IStatus status) {
			super(name, status);
		}

		@Override
		public boolean belongsTo(Object family) {
			if (family == null) {
				return false;
			}
			Class<?> clazz = family instanceof Class ? (Class<?>) family : family.getClass();
			return DummyFamilyJob.class.equals(clazz);
		}
	}

