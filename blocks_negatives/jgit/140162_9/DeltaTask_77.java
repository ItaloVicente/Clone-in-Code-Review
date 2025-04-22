			Collections.sort(topPaths, new Comparator<WeightedPath>() {
				@Override
				public int compare(WeightedPath a, WeightedPath b) {
					return a.slice.beginIndex - b.slice.beginIndex;
				}
			});
