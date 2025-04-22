
	public static class TestBuilder extends IncrementalProjectBuilder {

		static AtomicInteger builderCallCount = new AtomicInteger(0);

		public TestBuilder() {
			resetCallCount();
		}

		@Override
		protected IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor) {
			builderCallCount.incrementAndGet();
			return null;
		}

		static void resetCallCount() {
			builderCallCount.set(0);
		}
	}

