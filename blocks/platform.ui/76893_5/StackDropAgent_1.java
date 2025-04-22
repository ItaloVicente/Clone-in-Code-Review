			if (hiddenChildren < 0) {

			} else {
				itemRect.x = curX;
				itemRect.width = dropCTF.getBounds().width - curX;
				insertRect = dropCTF.getDisplay().map(dropCTF, null, itemRect);
				itemRects.add(insertRect);
			}
