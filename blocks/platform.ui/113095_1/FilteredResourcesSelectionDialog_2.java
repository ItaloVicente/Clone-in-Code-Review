						usedRegions++;
						continue;
					}
					int startlocation = indexOf(Pattern.compile(region), string.substring(currentIndex));
					if (startlocation == -1) {
						currentIndex = ++startingIndex;
						positions = new ArrayList<>();
						restart = true;
						break;
					}
					int regionIndex = 0;
					currentIndex += startlocation;
					for (char regionChar : region.toCharArray()) {
						if (regionChar == '\\') {
							continue;
						}
						regionIndex++;
						if (regionChar == '.' && regionIndex != 1) {
							positions.add(new Position(currentIndex, regionIndex - 1));
							currentIndex += regionIndex;
							regionIndex = 0;
