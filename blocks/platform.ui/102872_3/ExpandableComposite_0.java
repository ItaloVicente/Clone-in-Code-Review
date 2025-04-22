
				int gap = 0;
				if (size.y < height) {
					gap = (height - size.y) / 2;
				}

				textLabelCache.setBounds(x, ty + gap, size.x, size.y);
