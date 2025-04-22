			Arrays.sort(sortedElements, new Comparator<QuickAccessElement>() {
				@Override
				public int compare(QuickAccessElement e1, QuickAccessElement e2) {
					return e1.getSortLabel().compareTo(e2.getSortLabel());
				}
			});
