			if (gc.isClipped()) {
				Rectangle saveClipping = gc.getClipping();
				gc.setClipping(textBounds);
				textLayout.draw(gc, x, y);
				gc.setClipping(saveClipping);
			} else {
				x = textBounds.x;
				textLayout.draw(gc, x, y);
			}
