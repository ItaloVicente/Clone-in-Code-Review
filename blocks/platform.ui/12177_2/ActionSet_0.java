		MToolBarElement action = null;
		if (!IWorkbenchRegistryConstants.EXTENSION_EDITOR_ACTIONS.equals(element
				.getDeclaringExtension().getExtensionPointUniqueIdentifier())) {

			action = MenuHelper.createLegacyToolBarActionAdditions(application, element);
			if (action == null) {
				return;
			}
			action.getTransientData().put("Name", MenuHelper.getLabel(element)); //$NON-NLS-1$
			action.getTransientData().put("ActionSet", id); //$NON-NLS-1$
