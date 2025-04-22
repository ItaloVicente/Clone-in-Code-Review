					recordCursorPosition();
					popup = new ContentProposalPopup(null, proposals);
					popup.open();
					popup.getShell().addDisposeListener(event -> popup = null);
					internalPopupOpened();
					notifyPopupOpened();
