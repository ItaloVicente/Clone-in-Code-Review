			link.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					WorkbenchPlugin.getDefault().getPreferenceStore()
							.setValue(FULL_SCREEN_COMMAND_DO_NOT_SHOW_INFO_AGAIN_PREF_ID, btnDoNotShow.getSelection());
					close();
				}
			});
