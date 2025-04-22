			int y = textBounds.y + Math.max(0, (textBounds.height - layoutBounds.height) / 2);

			if (gc.isClipped()) {
				Rectangle saveClipping = gc.getClipping();
				gc.setClipping(textBounds);
				textLayout.draw(gc, x, y);
				gc.setClipping(saveClipping);
			} else {

				textLayout.draw(gc, textBounds.x, y);
