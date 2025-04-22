		} else if (client != null) {
			if (part.getTransientData().get(E4PartWrapper.E4_WRAPPER_KEY) instanceof E4PartWrapper) {
				IWorkbenchPart workbenchPart = (IWorkbenchPart) part.getTransientData()
						.get(E4PartWrapper.E4_WRAPPER_KEY);
				if (targeted) {
					notifyListeners(workbenchPart, (ISelection) selection, part.getElementId(),
							targetedPostSelectionListeners);
				} else {
					notifyListeners(workbenchPart, (ISelection) selection, postSelectionListeners);
				}
			}
