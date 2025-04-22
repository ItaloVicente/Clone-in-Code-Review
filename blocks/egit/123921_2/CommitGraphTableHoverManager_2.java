		} else if (e.x > maxRefX) {
			return new Information(commit.getShortMessage(),
					new Rectangle(maxRefX, itemBounds.y,
							itemBounds.x + itemBounds.width - maxRefX,
							itemBounds.height));
		} else if (e.x < minRefX) {
			return new Information(commit.getShortMessage(),
					new Rectangle(itemBounds.x, itemBounds.y,
							minRefX - itemBounds.x, itemBounds.height));
