			parentDeactivateListener = new Listener() {
				@Override
				public void handleEvent(Event event) {
					if (listenToParentDeactivate) {
						asyncClose();
					} else {
						listenToParentDeactivate = listenToDeactivate;
					}
