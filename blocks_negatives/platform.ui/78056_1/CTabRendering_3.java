				int[] rt = drawCircle(rightIndex + width - (radius * 2), circY,
						radius, RIGHT_BOTTOM);
				System.arraycopy(rt, 0, points, index, rt.length);
				index += rt.length;
				if (!active) {
					System.arraycopy(rt, rt.length - 4, inactive, inactive_index, 2);
					inactive[inactive_index] -= 1;
					inactive_index += 2;
				}
