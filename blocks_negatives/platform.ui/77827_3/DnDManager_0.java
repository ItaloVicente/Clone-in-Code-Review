			if (fr.width > 6) {
				Rectangle outerBounds = new Rectangle(fr.x - 3, fr.y - 3, fr.width + 6,
						fr.height + 6);
				if (bounds == null)
					bounds = outerBounds;
				bounds.add(outerBounds);
			} else {
				if (bounds == null)
					bounds = fr;
				bounds.add(fr);
			}
