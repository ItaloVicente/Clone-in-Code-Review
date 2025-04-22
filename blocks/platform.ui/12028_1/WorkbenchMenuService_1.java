		ToolBarManagerRenderer renderer = (ToolBarManagerRenderer) obj;
		MToolBar mToolBar = renderer.getToolBarModel(toolbarManager);
		if (mToolBar != null) {
			String tag = "toolbar:" + location.getPath(); //$NON-NLS-1$
			if (!mToolBar.getTags().contains(tag)) {
				mToolBar.getTags().add(tag);
			}
			return mToolBar;
		}

