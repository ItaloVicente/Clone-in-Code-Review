
		@Override
		public void doSave(IProgressMonitor monitor) throws CoreException {
			if (isDirty()) {
				performSave(monitor);
			}
		}
