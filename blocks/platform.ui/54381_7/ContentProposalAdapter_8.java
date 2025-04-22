					e.display.asyncExec(() -> {
						if (isValid()) {
							if (scrollbarClicked || hasFocus()) {
								return;
							}
							Shell activeShell = e.display.getActiveShell();
							if (activeShell == getShell()
									|| (infoPopup != null && infoPopup
											.getShell() == activeShell)) {
								return;
