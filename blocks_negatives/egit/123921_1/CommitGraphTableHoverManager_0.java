		int relativeX = e.x - itemBounds.x;
		for (int i = 0; i < commit.getRefCount(); i++) {
			Ref ref = commit.getRef(i);
			Point textSpan = renderer.getRefHSpan(ref);
			if ((textSpan != null)
					&& (relativeX >= textSpan.x && relativeX <= textSpan.y)) {

				String hoverText = getHoverText(ref, i, commit);
				int x = itemBounds.x + textSpan.x;
				int width = textSpan.y - textSpan.x;
				Rectangle rectangle = new Rectangle(x, itemBounds.y, width,
						itemBounds.height);
				return new Information(hoverText, rectangle);
