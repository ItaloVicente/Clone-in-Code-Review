				if (selEvent.item == null) {
					return;
				}
				fireSelectionEvent(selEvent);
				firePostSelectionEvent(selEvent);
			}
		};
	}
