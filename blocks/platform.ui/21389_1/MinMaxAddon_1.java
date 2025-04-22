
						MWindow tcWin = getWindow(tc);
						if (tcWin != null && (tcWin.getWidget() instanceof Shell)) {
							Shell tcShell = (Shell) tc.getWidget();
							if (!shellsToLayout.contains(tcShell))
								shellsToLayout.add(tcShell);
						}
