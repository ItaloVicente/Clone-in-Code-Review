	private final class ProgressReportingFilter extends TreeFilter {
		private int worked;

		private final ProgressMonitor monitor;

		private int count = 0;

		private boolean cancelled;

		private int stepSize;

		private ProgressReportingFilter(ProgressMonitor monitor
			this.worked = total;
			this.monitor = monitor;
			stepSize = total / 100;
			if (stepSize == 0)
				stepSize = Integer.MAX_VALUE;
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
				if (worked-- > 0)
					monitor.update(stepSize);
				if (monitor.isCancelled())
					cancelled = true;
			}
			return !cancelled;
		}

		@Override
		public TreeFilter clone() {
			throw new IllegalStateException(
					"Do not clone this kind of filter: "
							+ getClass().getName());
		}
	}

