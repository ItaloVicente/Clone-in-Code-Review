				item.addListener(SWT.Selection, new Listener() {

					@Override
					public void handleEvent(Event event) {
						ExtendedMarkersView view = getView();
						if (view != null) {
							view.disableAllFilters();
						}
