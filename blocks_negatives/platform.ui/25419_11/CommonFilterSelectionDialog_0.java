			Set checkedFilters = commonFiltersTab.getCheckedItems();

			String[] filterIdsToActivate = new String[checkedFilters.size()];
			int indx = 0;
			for (Iterator iterator = checkedFilters.iterator(); iterator
					.hasNext();) {
				ICommonFilterDescriptor descriptor = (ICommonFilterDescriptor) iterator
						.next();

				filterIdsToActivate[indx++] = descriptor.getId();

