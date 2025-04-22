			} else {
				boolean hideable = UIEvents.contains(event,
						UIEvents.EventTags.NEW_VALUE, HIDEABLE);
				if (UIEvents.contains(event, UIEvents.EventTags.NEW_VALUE,
						SHOW_RESTORE_MENU) || hideable) {
					Object obj = changedElement.getWidget();
					if (obj instanceof Control) {
						if (((Control) obj).getMenu() == null) {
							createToolControlMenu(changedElement,
									(Control) obj, hideable);
						}
					}
				}
