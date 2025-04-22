		case UIEvents.Dirtyable.DIRTY:
			Boolean dirtyState = (Boolean) newValue;
			String text = cti.getText();
			boolean hasAsterisk = text.length() > 0 && text.charAt(0) == '*';
			if (dirtyState.booleanValue()) {
				if (!hasAsterisk) {
					cti.setText('*' + text);
				}
			} else if (hasAsterisk) {
				cti.setText(text.substring(1));
			}
			break;
