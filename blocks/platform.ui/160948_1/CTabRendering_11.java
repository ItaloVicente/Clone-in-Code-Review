				int[] tmpPoints = new int[index];
				System.arraycopy(points, 0, tmpPoints, 0, index);
				gc.fillPolygon(tmpPoints);

				Color tempBorder = new Color(gc.getDevice(), 182, 188, 204);
				gc.setForeground(tempBorder);
				tempBorder.dispose();

				if (active) {
					gc.drawPolyline(tmpPoints);
				} else {
					gc.drawLine(inactive[0], inactive[1], inactive[2], inactive[3]);
					gc.drawLine(inactive[4], inactive[5], inactive[6], inactive[7]);
				}
				gc.setClipping((Rectangle) null);
			}
