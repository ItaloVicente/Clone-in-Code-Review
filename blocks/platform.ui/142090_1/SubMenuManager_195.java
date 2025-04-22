		super.setVisible(visible);
		if (mapMenuToWrapper != null) {
			Iterator<SubMenuManager> iter = mapMenuToWrapper.values().iterator();
			while (iter.hasNext()) {
				SubMenuManager wrapper = iter.next();
				wrapper.setVisible(visible);
			}
		}
	}

	@Override
