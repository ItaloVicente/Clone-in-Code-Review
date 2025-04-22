					for (char regionChar : region.toCharArray()) {
						if (regionChar == '\\') {
							continue;
						}
						regionIndex++;
						if (regionChar == '.' && regionIndex != 1) {
							positions.add(new Position(currentIndex, regionIndex - 1));
							currentIndex += regionIndex;
							regionIndex = 0;
						}
					}
