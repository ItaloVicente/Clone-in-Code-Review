	final Throttler throttledPostSelection;
	final PostSelectionEvent postSelectionEvent = new PostSelectionEvent();
	class PostSelectionEvent implements Runnable {
		volatile Event e;

		void setEvent(Event e) {
			this.e = e;
		}

		@Override
		public void run() {
			firePostSelectionEvent(new SelectionEvent(e));
			if ((CURRENT_METHOD & ARROW_KEYS_OPEN) != 0) {
				fireOpenEvent(new SelectionEvent(e));
			}
		}
	}
