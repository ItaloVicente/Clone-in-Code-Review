	private final class ProgressReportingFilter extends TreeFilter {

		private final ProgressMonitor monitor;

		private int count = 0;

		private int stepSize;

		private final int total;

		private ProgressReportingFilter(ProgressMonitor monitor
			this.monitor = monitor;
			this.total = total;
			stepSize = total / 100;
			if (stepSize == 0)
				stepSize = 1000;
		}

		@Override
		public boolean shouldBeRecursive() {
			return false;
		}

		@Override
		public boolean include(TreeWalk walker)
				throws MissingObjectException
				IncorrectObjectTypeException
			count++;
			if (count % stepSize == 0) {
				if (count <= total)
					monitor.update(stepSize);
				if (monitor.isCancelled())
					throw StopWalkException.INSTANCE;
			}
			return true;
		}

		@Override
		public TreeFilter clone() {
			throw new IllegalStateException(
					"Do not clone this kind of filter: "
							+ getClass().getName());
		}
	}

