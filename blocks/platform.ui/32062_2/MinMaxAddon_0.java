					Shell[] shells = winShell.getShells();
					for (Shell shell : shells) {
						if (!shell.isDisposed()) {
							shell.layout(true, true);
						}
					}
