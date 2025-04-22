		int outlineY = onBottom ? bottomY + bounds.height : bottomY - bounds.height - 1;
		int[] points = new int[12];

		if (itemIndex == 0 && bounds.x == -computeTrim(CTabFolderRenderer.PART_HEADER, SWT.NONE, 0, 0, 0, 0).x) {
			points[index++] = startX;
			points[index++] = bottomY;
		} else {
			if (active) {
				points[index++] = shadowEnabled ? SIDE_DROP_WIDTH : 0 + INNER_KEYLINE + OUTER_KEYLINE;
				points[index++] = bottomY;
			}
			points[index++] = startX;
			points[index++] = bottomY;
		}
