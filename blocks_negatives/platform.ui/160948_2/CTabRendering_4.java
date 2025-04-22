			int[] ltt = drawCircle(leftIndex, circY, radius, CirclePart.left(onBottom));
			if (!onBottom) {
				mirrorCirclePoints(ltt);
			}
			System.arraycopy(ltt, 0, points, index, ltt.length);
			index += ltt.length;
