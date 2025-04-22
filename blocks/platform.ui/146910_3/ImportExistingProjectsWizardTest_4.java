
	public static class TestBuilder extends IncrementalProjectBuilder {

		static AtomicBoolean builderCalled = new AtomicBoolean(false);

		@Override
		protected IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor) {
			builderCalled.set(true);
			return null;
		}
	}

