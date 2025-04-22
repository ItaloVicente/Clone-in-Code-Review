			IWorkbenchPart workbenchPart = getWrappedPart((CompatibilityPart) client);
			if (workbenchPart != null) {
				PartSite site = (PartSite) workbenchPart.getSite();
				site.activateActionBars(true);

				IActionBars actionBars = site.getActionBars();
				if (actionBars instanceof EditorActionBars) {
					((EditorActionBars) actionBars).partChanged(workbenchPart);
				}
