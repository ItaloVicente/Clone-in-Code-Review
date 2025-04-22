		if (!containsNonproject && !closedProjects.isEmpty()) {
			if (closedProjects.size() > 1) {
				openProjectAction.setText(IDEWorkbenchMessages.OpenResourceAction_text_plural);
				openProjectAction.setToolTipText(IDEWorkbenchMessages.OpenResourceAction_toolTip_plural);
			} else {
				openProjectAction.setText(IDEWorkbenchMessages.OpenResourceAction_text);
				openProjectAction.setToolTipText(IDEWorkbenchMessages.OpenResourceAction_toolTip);
			}
			openProjectAction.selectionChanged(selection);
			menu.appendToGroup(ICommonMenuConstants.GROUP_BUILD, openProjectAction);
		}
		if (!containsNonproject && !openProjects.isEmpty()) {
			if (openProjects.size() > 1) {
				closeProjectAction.setText(IDEWorkbenchMessages.CloseResourceAction_text_plural);
				closeProjectAction.setToolTipText(IDEWorkbenchMessages.CloseResourceAction_toolTip_plural);
			} else {
				closeProjectAction.setText(IDEWorkbenchMessages.CloseResourceAction_text);
				closeProjectAction.setToolTipText(IDEWorkbenchMessages.CloseResourceAction_toolTip);
