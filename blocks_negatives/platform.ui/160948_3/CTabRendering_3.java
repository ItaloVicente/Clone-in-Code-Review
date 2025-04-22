			int leftIndex = circX;
			if (itemIndex == 0) {
				if (parent.getSelectionIndex() != 0)
					leftIndex -= 1;
				points[index++] = leftIndex - radius;
				points[index++] = bottomY;
