		int outlineY = onBottom ? bottomY + bounds.height : bottomY - bounds.height;
		int[] points = new int[20];

		int margin = shadowEnabled ? SIDE_DROP_WIDTH
				: (Objects.equals(outerKeyline, tabOutlineColor) || Objects.equals(outerKeyline, parent.getBackground())
						? 0
						: 1);
		points[index++] = margin;
		points[index++] = bottomY;
		points[index++] = startX;
		points[index++] = bottomY;
