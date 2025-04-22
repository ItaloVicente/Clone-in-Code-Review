			IWorkbenchPart activePart = getWrappedPart((CompatibilityEditor) client);
			if (activePart == null) {
				return;
			}
			String activeId = activePart.getSite().getId();

