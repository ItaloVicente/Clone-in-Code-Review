				} else if (item != null && item.isLinkAt(offset)) {
					styledText.setCursor(busyCursor);
					item.getLinkAt(offset).ifPresent(l -> Program.launch(l));
					StyleRange selectionRange = getCurrentRange();
					styledText.setSelectionRange(selectionRange.start, selectionRange.length);
					styledText.setCursor(null);
