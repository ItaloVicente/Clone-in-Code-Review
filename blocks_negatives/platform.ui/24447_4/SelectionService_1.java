			Object client = part.getObject();
			if (client instanceof CompatibilityPart) {
				IWorkbenchPart workbenchPart = ((CompatibilityPart) client).getPart();
				notifyPostSelectionListeners(part.getElementId(), workbenchPart,
						(ISelection) selection);
			}
