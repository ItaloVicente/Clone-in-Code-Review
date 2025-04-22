			@Override
			public void widgetSelected(SelectionEvent e) {
				ViewerFilter[] currentFilters = tree.getFilters();
				ViewerFilter[] newFilters = null;
				if (((Button) e.widget).getSelection()) {
					newFilters = new ViewerFilter[currentFilters.length + 1];
					System.arraycopy(currentFilters, 0, newFilters, 0, currentFilters.length);
					newFilters[newFilters.length - 1] = existingProjectsFilter;
				} else {
					List<ViewerFilter> filters = new ArrayList<>(
							currentFilters.length > 0 ? currentFilters.length - 1 : 0);
					for (ViewerFilter filter : currentFilters) {
						if (filter != existingProjectsFilter) {
							filters.add(filter);
						}
					}
					newFilters = filters.toArray(new ViewerFilter[filters.size()]);
				}
				tree.setFilters(newFilters);
			}
		});
