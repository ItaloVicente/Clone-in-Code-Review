
							if (popup == null) {
								switch (e.character) {
								case SWT.CR:
								case SWT.LF:
								case SWT.ESC:
									receivedKeyDown = true;
									break;
								}
							}
