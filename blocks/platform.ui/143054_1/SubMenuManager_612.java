			item = unwrap(item);
		}

		if (item instanceof IMenuManager) {
			IMenuManager menu = (IMenuManager) item;
			item = getWrapper(menu);
