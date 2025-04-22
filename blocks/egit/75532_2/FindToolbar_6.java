				currentPositionLabel.setText(position + '/' + total);
			}
			if (currentPosition < 0) {
				currentPosition = 1;
				int ix = findResults.getFirstIndex();
				sendEvent(null, ix);
