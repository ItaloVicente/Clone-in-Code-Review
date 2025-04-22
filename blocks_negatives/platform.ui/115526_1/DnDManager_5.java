	DragDetectListener dragDetector = new DragDetectListener() {
		@Override
		public void dragDetected(DragDetectEvent e) {
			if (dragging || e.widget.isDisposed()) {
				return;
			}
