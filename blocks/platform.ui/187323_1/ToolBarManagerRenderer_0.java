

	}


	private void updateIconBasedOnCSS(MToolBarElement toolbarElement) {
		if (toolbarElement instanceof MHandledToolItem) {
			var handledToolbarElement = (MHandledToolItem) toolbarElement;
			handledToolbarElement.setIconURI("platform:/plugin/org.eclipse.ui/icons/full/etool16/print_edit.png"); //$NON-NLS-1$
		}
		if (toolbarElement instanceof MDirectToolItem) {
			var handledToolbarElement = (MDirectToolItem) toolbarElement;
			handledToolbarElement.setIconURI("platform:/plugin/org.eclipse.ui/icons/full/etool16/print_edit.png"); //$NON-NLS-1$
		}
