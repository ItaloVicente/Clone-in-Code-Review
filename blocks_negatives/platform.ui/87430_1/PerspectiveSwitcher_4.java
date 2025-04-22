		bar.addDragDetectListener(new DragDetectListener() {
			@Override
			public void dragDetected(DragDetectEvent e) {
				if (dragItem != null) {
					dragging = true;
					track(e);
				}
