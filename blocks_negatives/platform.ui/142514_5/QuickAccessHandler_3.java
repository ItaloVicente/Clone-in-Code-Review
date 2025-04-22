		MWindow mWindow = ((WorkbenchWindow) window).getModel();
		EModelService modelService = mWindow.getContext().get(EModelService.class);
		MToolControl searchField = (MToolControl) modelService.find("SearchField", mWindow); //$NON-NLS-1$
		if (searchField != null && searchField.isVisible()) {
			Control control = (Control) searchField.getWidget();
			if (control != null && control.isVisible()) {
				Control previousFocusControl = control.getDisplay().getFocusControl();
				control.setFocus();
				SearchField field = (SearchField) searchField.getObject();
				field.activate(previousFocusControl);
				return null;
			}
		}

		displayQuickAccessDialog(window, executionEvent.getCommand());
