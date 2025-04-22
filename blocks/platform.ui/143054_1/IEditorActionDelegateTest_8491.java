		MockEditorPart editor = (MockEditorPart) widget;
		MockEditorActionBarContributor contributor = (MockEditorActionBarContributor) editor
				.getEditorSite().getActionBarContributor();
		IMenuManager mgr = contributor.getActionBars().getMenuManager();
		ActionUtil.runActionWithLabel(this, mgr, "Mock Action");
	}
