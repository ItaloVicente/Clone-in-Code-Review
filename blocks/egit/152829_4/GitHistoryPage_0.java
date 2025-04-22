				sortedFilters
						.sort(Comparator
								.comparing(RefFilter::isPreconfigured,
										Comparator.reverseOrder())
								.thenComparing(RefFilter::getFilterString,
										String.CASE_INSENSITIVE_ORDER));
