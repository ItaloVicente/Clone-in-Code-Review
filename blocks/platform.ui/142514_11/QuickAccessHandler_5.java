		SearchField field = null;
		MWindow mWindow = ((WorkbenchWindow) window).getModel();
		EModelService modelService = mWindow.getContext().get(EModelService.class);
		MToolControl searchField = (MToolControl) modelService.find("SearchField", mWindow); //$NON-NLS-1$
		if (searchField != null && searchField.isVisible()) {
			Control control = (Control) searchField.getWidget();
			if (control != null) {
				field = (SearchField) searchField.getObject();
			}
		}

		PopupDialog popupDialog = new QuickAccessDialog(window, field == null ? null : field.getQuickAccessSearchText(),
				command);
