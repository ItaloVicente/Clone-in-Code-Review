		Iterator resources = getSelectedResources().iterator();
		while (resources.hasNext()) {
			IProject currentResource = (IProject) resources.next();
			if (!currentResource.isOpen()) {
				if (hasClosedProjects) {
					setText(IDEWorkbenchMessages.OpenResourceAction_text_plural);
					setToolTipText(IDEWorkbenchMessages.OpenResourceAction_toolTip_plural);
					break;
				}
				hasClosedProjects = true;
			}
