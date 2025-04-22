		MUIElement parent = getParentModel();
		if (parent != null && isOnTop()) {
			Object renderer = parent.getRenderer();
			if (renderer instanceof StackRenderer) {
				StackRenderer stackRenderer = (StackRenderer) renderer;
				CTabFolder folder = (CTabFolder) parent.getWidget();
				stackRenderer.adjustTopRight(folder);
			}
		}

