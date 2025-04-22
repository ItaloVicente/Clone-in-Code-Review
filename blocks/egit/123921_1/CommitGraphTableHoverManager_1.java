		int minRefX = -1;
		int maxRefX = itemBounds.x;
		if (commit.getRefCount() > 0) {
			int relativeX = e.x - itemBounds.x;
			for (int i = 0; i < commit.getRefCount(); i++) {
				Ref ref = commit.getRef(i);
				Point textSpan = renderer.getRefHSpan(ref);
				if (textSpan != null) {
					if (relativeX >= textSpan.x && relativeX <= textSpan.y) {
						String hoverText = getHoverText(ref, i, commit);
						int x = itemBounds.x + textSpan.x;
						int width = textSpan.y - textSpan.x;
						Rectangle rectangle = new Rectangle(x, itemBounds.y,
								width, itemBounds.height);
						return new Information(hoverText, rectangle);
					} else {
						if (minRefX < 0) {
							minRefX = itemBounds.x + textSpan.x;
						} else {
							minRefX = Math.min(minRefX, itemBounds.x + textSpan.x);
						}
						maxRefX = Math.max(maxRefX, itemBounds.x + textSpan.y);
					}
				}
			}
		}
		if (minRefX < 0) {
			if (e.x - itemBounds.x > itemBounds.width / 2) {
				return new Information(commit.getShortMessage(),
						new Rectangle(itemBounds.x + itemBounds.width / 2,
								itemBounds.y, itemBounds.width / 2,
								itemBounds.height));
			} else {
				int left = Math.max(itemBounds.x, e.x - 10);
				return new Information(commit.getShortMessage(),
						new Rectangle(left, itemBounds.y,
								itemBounds.x + itemBounds.width - left,
								itemBounds.height));
