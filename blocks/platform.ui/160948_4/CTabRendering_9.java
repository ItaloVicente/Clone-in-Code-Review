				if (!active) {
					System.arraycopy(rt, rt.length - 4, inactive, inactive_index, 2);
					inactive[inactive_index] -= 1;
					inactive_index += 2;
				}

				points[index++] = bounds.width + rightIndex - radius;
				points[index++] = bottomY;
