				Runnable runnable = () -> {
					pendingDescriptionUpdate = true;
					try {
						Thread.sleep(POPUP_DELAY);
					} catch (InterruptedException e) {
					}
					if (!isValid()) {
						return;
					}
					getShell().getDisplay().syncExec(() -> {
						IContentProposal p = getSelectedProposal();
						if (p != null) {
							String description = p.getDescription();
							if (description != null) {
								if (infoPopup == null) {
									infoPopup = new InfoPopupDialog(
											getShell());
									infoPopup.open();
									infoPopup
											.getShell()
											.addDisposeListener(
													event -> infoPopup = null);
