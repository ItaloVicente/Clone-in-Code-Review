		IWorkbenchPartSite partSite = workbenchPartSite;
		if (parent != null) {
			KeyBindingService currentParent = parent;
			while (currentParent != null) {
				partSite = currentParent.workbenchPartSite;
				currentParent = currentParent.parent;
			}
		}

