			int[] rt = drawCircle(rightIndex + width - (radius * 2), circY, radius, CirclePart.right(onBottom));
			if (!onBottom) {
				mirrorCirclePoints(rt);
			}
			System.arraycopy(rt, 0, points, index, rt.length);
			index += rt.length;
