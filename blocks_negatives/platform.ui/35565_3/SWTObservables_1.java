
	private static class DisplayRealm extends Realm {
		private Display display;

		/**
		 * @param display
		 */
		private DisplayRealm(Display display) {
			this.display = display;
		}

		@Override
		public boolean isCurrent() {
			return Display.getCurrent() == display;
		}

		@Override
		public void asyncExec(final Runnable runnable) {
			Runnable safeRunnable = new Runnable() {
				@Override
				public void run() {
					safeRun(runnable);
				}
			};
			if (!display.isDisposed()) {
				display.asyncExec(safeRunnable);
			}
		}

		@Override
		public void timerExec(int milliseconds, final Runnable runnable) {
			if (!display.isDisposed()) {
				Runnable safeRunnable = new Runnable() {
					@Override
					public void run() {
						safeRun(runnable);
					}
				};
				display.timerExec(milliseconds, safeRunnable);
			}
		}

		@Override
		public int hashCode() {
			return (display == null) ? 0 : display.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final DisplayRealm other = (DisplayRealm) obj;
			if (display == null) {
				if (other.display != null)
					return false;
			} else if (!display.equals(other.display))
				return false;
			return true;
		}
	}
