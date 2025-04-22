						};
						restoreDialog();
						QuickAccessDialog.this.invokingCommand = invokingCommand;
						if (QuickAccessDialog.this.invokingCommand != null
								&& !QuickAccessDialog.this.invokingCommand.isDefined()) {
							QuickAccessDialog.this.invokingCommand = null;
						} else {
							getInvokingCommandKeySequences();
