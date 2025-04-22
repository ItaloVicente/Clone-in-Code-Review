						if (popup == null) {
							switch (e.detail) {
							case SWT.TRAVERSE_TAB_NEXT:
							case SWT.TRAVERSE_TAB_PREVIOUS:
								receivedKeyDown = true;
								break;
							}
						}
