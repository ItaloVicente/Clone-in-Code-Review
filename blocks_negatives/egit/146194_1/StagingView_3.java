
		presentation = readPresentation(UIPreferences.STAGING_VIEW_PRESENTATION,
				Presentation.LIST);
		switch (presentation) {
		case LIST:
			listPresentationAction.setChecked(true);
			setExpandCollapseActionsVisible(false, false, false);
			setExpandCollapseActionsVisible(true, false, false);
			break;
		case TREE:
			treePresentationAction.setChecked(true);
			break;
		case COMPACT_TREE:
			compactTreePresentationAction.setChecked(true);
			break;
		default:
			break;
		}
