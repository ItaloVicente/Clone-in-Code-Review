				sortedFilters.sort(new Comparator<RefFilter>() {

					private int category(RefFilter filter) {
						if (filter.isPreconfigured()) {
							return 100;
						}
						return 1000;
					}

					@Override
					public int compare(RefFilter o1, RefFilter o2) {
						int cat1 = category(o1);
						int cat2 = category(o2);

						if (cat1 != cat2) {
							return cat1 - cat2;
						}

						String name1 = o1.getFilterString();
						String name2 = o2.getFilterString();

						return name1.compareTo(name2);
					}
				});
