
			filtersMenu = new MenuManager(CommonNavigatorMessages.FilterActionGroup_RecentFilters);
			menuListener = new IMenuListener() {

				@Override
				public void menuAboutToShow(IMenuManager manager) {
					filtersMenu.removeAll();
					addLRUFilterActions(filtersMenu);
				}

			};
