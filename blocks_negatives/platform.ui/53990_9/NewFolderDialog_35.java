				new Listener() {
					@Override
					public void handleEvent(Event e) {
						validateLinkedResource();
						firstLinkCheck = false;
					}
