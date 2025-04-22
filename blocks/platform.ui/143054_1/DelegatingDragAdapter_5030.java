					listener.dragStart(event);
				}
			});
			if (event.doit) { // the listener can handle this drag
				transfers.add(listener.getTransfer());
				activeListeners.add(listener);
			}
			doit |= event.doit;
		}
