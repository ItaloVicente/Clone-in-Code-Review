			public void run() throws Exception {	
				
				CommonFilterDescriptor[] visibleFilterDescriptors = CommonFilterDescriptorManager.getInstance().findVisibleFilters(contentService);
				
				for (CommonFilterDescriptor filterDescription : visibleFilterDescriptors) {
					if (!filterDescription.isVisibleInUi() && filterDescription.isActiveByDefault()) {
						activeFilters.add(filterDescription.getId());
					}
	
				}
				
