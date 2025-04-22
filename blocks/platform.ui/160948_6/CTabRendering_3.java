		if (cornerSize == SQUARE_CORNER) {
			startX = bounds.x;
			endX = bounds.x + bounds.width - 1;
			selectionX1 = startX;
			selectionY1 = bottomY;
			selectionX2 = endX;
			selectionY2 = bottomY;

			gc.fillRectangle(bounds);
		} else {
			int width = bounds.width;
			int[] points = new int[1024];
			int index = 0;
			int radius = cornerSize / 2;
			int circX = bounds.x + radius;
			int circY = onBottom ? bounds.y + bounds.height + 1 - header - radius : bounds.y - 1 + radius;
			if (itemIndex == 0 && bounds.x == -computeTrim(CTabFolderRenderer.PART_HEADER, SWT.NONE, 0, 0, 0, 0).x) {
				circX -= 1;
				points[index++] = circX - radius;
				points[index++] = bottomY;

				points[index++] = selectionX1 = circX - radius;
				points[index++] = selectionY1 = bottomY;
			} else {
				if (active) {
					points[index++] = shadowEnabled ? SIDE_DROP_WIDTH : 0 + INNER_KEYLINE + OUTER_KEYLINE;
					points[index++] = bottomY;
				}
				points[index++] = selectionX1 = bounds.x;
				points[index++] = selectionY1 = bottomY;
			}

			int[] ltt = drawCircle(circX, circY, radius, CirclePart.left(onBottom));
			startX = ltt[6];
			if (!onBottom) {
				mirrorCirclePoints(ltt);
			}
			System.arraycopy(ltt, 0, points, index, ltt.length);
			index += ltt.length;
			int[] rt = drawCircle(circX + width - (radius * 2), circY, radius, CirclePart.right(onBottom));
			endX = rt[rt.length - 4];
			if (!onBottom) {
				mirrorCirclePoints(rt);
			}
			System.arraycopy(rt, 0, points, index, rt.length);
			index += rt.length;

			points[index++] = selectionX2 = bounds.width + circX - radius;
			points[index++] = selectionY2 = bottomY;

			if (active) {
				points[index++] = parent.getSize().x
						- (shadowEnabled ? SIDE_DROP_WIDTH : 0 + INNER_KEYLINE + OUTER_KEYLINE);
				points[index++] = bottomY;
			}


			tmpPoints = new int[index];
			System.arraycopy(points, 0, tmpPoints, 0, index);
			gc.fillPolygon(tmpPoints);
		}

