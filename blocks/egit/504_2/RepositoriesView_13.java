			@Override
			public void run() {
				IEclipsePreferences prefs = getPrefs();
				prefs.putBoolean(PREFS_SYNCED, isChecked());
				try {
					prefs.flush();
				} catch (BackingStoreException e) {
				}
				if (isChecked()) {
					ISelectionService srv = (ISelectionService) getSite()
							.getService(ISelectionService.class);
					reactOnSelection(srv.getSelection());
				}

			}

		};

		linkWithSelectionAction.setToolTipText(UIText.RepositoriesView_LinkWithSelection_action);

		linkWithSelectionAction.setImageDescriptor(UIIcons.ELCL16_SYNCED);

		linkWithSelectionAction.setChecked(getPrefs().getBoolean(PREFS_SYNCED,
				false));

		getViewSite().getActionBars().getToolBarManager().add(
				linkWithSelectionAction);

		refreshAction = new Action(UIText.RepositoriesView_Refresh_Button) {
