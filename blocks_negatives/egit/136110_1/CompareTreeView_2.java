					if (orFilters.size() > 1) {
						TreeFilter andFilter = AndTreeFilter.create(new NotIgnoredFilter(baseTreeIndex),
								OrTreeFilter.create(orFilters));
						tw.setFilter(andFilter);
					} else if (orFilters.size() == 1) {
						TreeFilter andFilter = AndTreeFilter.create(new NotIgnoredFilter(baseTreeIndex),
								orFilters.get(0));
						tw.setFilter(andFilter);
					} else
