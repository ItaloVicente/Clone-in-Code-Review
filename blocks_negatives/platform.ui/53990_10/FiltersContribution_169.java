					return new Listener() {

						@Override
						public void handleEvent(Event event) {
							if (extendedView != null)
								extendedView.toggleFilter(filter);
						}
