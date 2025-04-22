					e.display.asyncExec(new Runnable() {
						@Override
						public void run() {
							if (isValid()) {
								if (scrollbarClicked || hasFocus()) {
									return;
								}
								Shell activeShell = e.display.getActiveShell();
								if (activeShell == getShell()
										|| (infoPopup != null && infoPopup
												.getShell() == activeShell)) {
									return;
								}
								/*
								 * System.out.println(e);
								 * System.out.println(e.display.getFocusControl());
								 * System.out.println(e.display.getActiveShell());
								 */
								close();
