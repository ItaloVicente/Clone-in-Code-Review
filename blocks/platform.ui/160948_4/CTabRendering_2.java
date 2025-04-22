			int[] rt = drawCircle(circX + width - (radius * 2), circY, radius, CirclePart.RIGHT_TOP);
			System.arraycopy(rt, 0, points, index, rt.length);
			index += rt.length;
			points[index++] = circX;
			points[index++] = circY - radius;

			int[] tempPoints = new int[index];
			System.arraycopy(points, 0, tempPoints, 0, index);
			gc.fillPolygon(tempPoints);

			shape = tempPoints;
		}
