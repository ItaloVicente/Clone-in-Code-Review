
		if (item instanceof MMenu) {
			MenuManager itemManager = renderer.getManager((MMenu) item);
			if (itemManager != null) {
				if (!itemManager.isVisible()) {
					return false;
				}
			}
		}

