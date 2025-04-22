	private static abstract class EarlyStartupJob extends Job {
		public EarlyStartupJob(JobGroup group) {
			super("Workbench early stattup job"); //$NON-NLS-1$
			setSystem(true);
			setJobGroup(group);
		}

		@Override
		public boolean belongsTo(Object family) {
			return EARLY_STARTUP_FAMILY.equals(family);
		}
	}

