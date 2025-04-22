				}

				List pluginFilters = FiltersContentProvider.getDefaultFilters();
				for (Iterator iter = pluginFilters.iterator(); iter.hasNext();) {
					String element = (String) iter.next();
					if (!selectedFilters.contains(element) && !unSelectedFilters.contains(element)) {
