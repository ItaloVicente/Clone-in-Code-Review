		}
		return null;
	}

	protected Font getFont() {
		return JFaceResources.getFont(getSymbolicFontName());
	}

	public MenuManager getMenuBarManager() {
		return menuBarManager;
	}

	protected StatusLineManager getStatusLineManager() {
		return statusLineManager;
	}

	public String getSymbolicFontName() {
		return JFaceResources.TEXT_FONT;
	}

	public ToolBarManager getToolBarManager() {
		if (toolBarManager instanceof ToolBarManager) {
