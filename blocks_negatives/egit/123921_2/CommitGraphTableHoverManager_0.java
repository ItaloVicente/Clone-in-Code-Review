			if ((textSpan != null)
					&& (relativeX >= textSpan.x && relativeX <= textSpan.y)) {

				String hoverText = getHoverText(ref, i, commit);
				int x = itemBounds.x + textSpan.x;
				int width = textSpan.y - textSpan.x;
				Rectangle rectangle = new Rectangle(x, itemBounds.y, width,
						itemBounds.height);
				return new Information(hoverText, rectangle);
