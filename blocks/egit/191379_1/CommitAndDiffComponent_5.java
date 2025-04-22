
	private class Resizer extends ControlAdapter implements Runnable {

		private final Control control;

		private long lastEventTime;

		Resizer(Control control) {
			this.control = control;
		}

		@Override
		public void controlResized(ControlEvent e) {
			lastEventTime = System.currentTimeMillis();
			schedule();
		}

		private void schedule() {
			control.getDisplay().timerExec(300, this);
		}

		@Override
		public void run() {
			if (control.isDisposed()) {
				return;
			}
			long now = System.currentTimeMillis();
			if (now - lastEventTime > 300) {
				resizeCommentAndDiffScrolledComposite();
			} else {
				schedule();
			}
		}
	}
