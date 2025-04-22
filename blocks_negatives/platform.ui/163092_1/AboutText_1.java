					if (item != null) {
						int offset = text.getSelection().x + 1;

						if (item.isLinkAt(offset)) {
							text.setCursor(busyCursor);
							item.getLinkAt(offset).ifPresent(l -> Program.launch(l));
							StyleRange selectionRange = getCurrentRange();
							text.setSelectionRange(selectionRange.start, selectionRange.length);
							text.setCursor(null);
						}
					}
					return;
