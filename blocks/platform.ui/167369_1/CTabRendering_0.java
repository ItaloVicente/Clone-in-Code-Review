		points[index++] = parentSize.x - (shadowEnabled ? SIDE_DROP_WIDTH : 0 + INNER_KEYLINE + OUTER_KEYLINE);
		points[index++] = parentSize.y - (shadowEnabled ? SIDE_DROP_WIDTH : 0 + INNER_KEYLINE + OUTER_KEYLINE);

		points[index++] = points[0];
		points[index++] = parentSize.y - (shadowEnabled ? SIDE_DROP_WIDTH : 0 + INNER_KEYLINE + OUTER_KEYLINE);

		points[index++] = points[0];
		points[index++] = points[1];

