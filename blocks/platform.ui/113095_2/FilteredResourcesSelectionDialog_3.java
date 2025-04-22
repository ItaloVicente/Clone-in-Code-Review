			} while (restart && currentIndex < string.length());
			if (usedRegions != regions.length && string.toLowerCase().startsWith(originalMatching.toLowerCase())) {
				positions = new ArrayList<>();
				positions.add(new Position(0, originalMatching.length()));
			}
			return positions;
