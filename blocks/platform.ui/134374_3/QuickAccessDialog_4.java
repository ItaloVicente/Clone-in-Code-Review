				}
			};
			restoreDialog();
			QuickAccessDialog.this.invokingCommand = invokingCommand;
			if (QuickAccessDialog.this.invokingCommand != null && !QuickAccessDialog.this.invokingCommand.isDefined()) {
				QuickAccessDialog.this.invokingCommand = null;
			} else {
				getInvokingCommandKeySequences();
			}
			create();
		});
		if (initialSearchText == null) {
			initialSearchText = ""; //$NON-NLS-1$
		}
		filterText.setText(initialSearchText);
		filterText.setMessage(QuickAccessMessages.QuickAccess_StartTypingToFindMatches);
		filterText.setSelection(filterText.getText().length());
		QuickAccessDialog.this.contents.refresh(initialSearchText);

