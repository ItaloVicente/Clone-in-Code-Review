				return new Listener() {
					@Override
					public void handleEvent(Event event) {
						MenuItem item = (MenuItem) event.widget;
						if (item.getSelection() && view != null) {
							view.setCategoryGroup(gr);
						}
