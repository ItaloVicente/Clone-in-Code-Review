				if (!active) {
					System.arraycopy(ltt, 0, inactive, inactive_index, 2);
					inactive_index += 2;
				}

				int[] rt = drawCircle(rightIndex + width - (radius * 2), circY, radius, RIGHT_TOP);
				for (int i = 0; i < rt.length / 2; i += 2) {
					int tmp = rt[i];
					rt[i] = rt[rt.length - i - 2];
					rt[rt.length - i - 2] = tmp;
					tmp = rt[i + 1];
					rt[i + 1] = rt[rt.length - i - 1];
					rt[rt.length - i - 1] = tmp;
				}
				System.arraycopy(rt, 0, points, index, rt.length);
				index += rt.length;
				if (!active) {
					System.arraycopy(rt, rt.length - 4, inactive, inactive_index, 2);
					inactive[inactive_index] -= 1;
					inactive_index += 2;
				}
			} else {
				int[] ltt = drawCircle(leftIndex, circY, radius, LEFT_BOTTOM);
				System.arraycopy(ltt, 0, points, index, ltt.length);
				index += ltt.length;
