
			filtersMenu = new MenuManager(CommonNavigatorMessages.FilterActionGroup_Filters);
			menuListener = new IMenuListener() {

				@Override
				public void menuAboutToShow(IMenuManager manager) {
					filtersMenu.removeAll();
					addLRUFilterActions(filtersMenu);
				}

			};
