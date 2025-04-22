	/** True is the given model represents an hidden editor */
	protected boolean isHiddenEditor(MPart model) {
		if (model == null || model.getParent() == null || !(model.getParent().getRenderer() instanceof StackRenderer)) {
			return false;
		}
		StackRenderer renderer = (StackRenderer) model.getParent().getRenderer();
		CTabItem item = renderer.findItemForPart(model);
		return (item != null && !item.isShowing());
	}

	private Font getFont(boolean hidden, boolean active) {
		ITheme theme = PlatformUI.getWorkbench().getThemeManager().getCurrentTheme();
		if (active) {
			return theme.getFontRegistry().getItalic(IWorkbenchThemeConstants.TAB_TEXT_FONT);
		}
		if (hidden) {
			return theme.getFontRegistry().getBold(IWorkbenchThemeConstants.TAB_TEXT_FONT);
		}
		return theme.getFontRegistry().get(IWorkbenchThemeConstants.TAB_TEXT_FONT);
	}

