				if (dest != null && src.equals(dest)) {
					srcIx++;
					destIx++;
					if (force) {
						dest.update();
					}
					continue;
