				StyledText text = (StyledText) event.widget;
				if (event.character == ' ' || event.character == SWT.CR) {
					if (text != null) {
						WelcomeItem item = (WelcomeItem) text.getData();

						int offset = text.getSelection().x + 1;

						if (item.isLinkAt(offset)) {
							text.setCursor(busyCursor);
							item.triggerLinkAt(offset);
							StyleRange selectionRange = getCurrentLink(text);
							text.setSelectionRange(selectionRange.start,
									selectionRange.length);
							text.setCursor(null);
						}
					}
					return;
				}

				if (event.keyCode == SWT.PAGE_DOWN) {
					focusOn(nextText(text), 0);
					return;
				}

				if (event.keyCode == SWT.PAGE_UP) {
					focusOn(previousText(text), 0);
					return;
				}
			}
		});

		styledText.addFocusListener(new FocusAdapter() {
			@Override
