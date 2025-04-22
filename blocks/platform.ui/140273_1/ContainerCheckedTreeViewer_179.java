				Widget item = findItem(event.getElement());
				if (item instanceof TreeItem) {
					initializeItem((TreeItem) item);
				}
			}
		});
	}
