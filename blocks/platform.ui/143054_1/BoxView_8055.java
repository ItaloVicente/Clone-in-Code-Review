		paintCanvas.addMouseMoveListener(event -> {
			if (dragInProgress) {
				clearRubberBandSelection();
				tempPosition.x = event.x;
				tempPosition.y = event.y;
				addRubberBandSelection();
			} else if (moveInProgress) {
				clearRubberBandSelection();
				anchorPosition.x = event.x - diffX;
				anchorPosition.y = event.y - diffY;
				tempPosition.x = anchorPosition.x + movingBox.getWidth();
				tempPosition.y = anchorPosition.y + movingBox.getHeight();
				addRubberBandSelection();
