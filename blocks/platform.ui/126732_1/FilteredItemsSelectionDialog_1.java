		final String filterPattern;
		final Comparator<Object> itemsComparator;

		HistoryComparator(){
			itemsComparator = getItemsComparator();
			if (currentlyCompletingFilter != null) {
				filterPattern = currentlyCompletingFilter.getPattern();
			} else {
				filterPattern = null;
			}
		}
