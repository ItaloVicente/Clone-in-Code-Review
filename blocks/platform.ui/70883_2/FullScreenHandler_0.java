			GridData gd = new GridData(GridData.BEGINNING | GridData.FILL_BOTH);
			gd.horizontalIndent = PopupDialog.POPUP_HORIZONTALSPACING;
			gd.verticalIndent = PopupDialog.POPUP_VERTICALSPACING;
			link.setLayoutData(gd);

			Button btnDoNotShow = new Button(composite, SWT.CHECK);
			btnDoNotShow.setText(messageDoNotShowAgain);
			btnDoNotShow.setSelection(WorkbenchPlugin.getDefault().getPreferenceStore()
					.getBoolean(FULL_SCREEN_COMMAND_DO_NOT_SHOW_INFO_AGAIN_PREF_ID));
			GridData gd2 = new GridData(GridData.BEGINNING | GridData.FILL_BOTH);
			gd2.horizontalIndent = PopupDialog.POPUP_HORIZONTALSPACING;
			gd2.verticalIndent = PopupDialog.POPUP_VERTICALSPACING;
			btnDoNotShow.setLayoutData(gd2);
