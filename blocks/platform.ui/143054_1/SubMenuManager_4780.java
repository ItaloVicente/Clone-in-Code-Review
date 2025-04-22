		return false;
	}

	protected IMenuManager getWrapper(IMenuManager mgr) {
		if (mapMenuToWrapper == null) {
			mapMenuToWrapper = new HashMap<>(4);
		}
		SubMenuManager wrapper = mapMenuToWrapper.get(mgr);
		if (wrapper == null) {
			wrapper = wrapMenu(mgr);
			mapMenuToWrapper.put(mgr, wrapper);
		}
		return wrapper;
	}

	@Override
