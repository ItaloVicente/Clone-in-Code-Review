			}

			switch (resultIDs[r]) {
			case IDialogConstants.YES_ID: {
				v.setValue(true);
				return true;
			}
			case IDialogConstants.NO_ID: {
				v.setValue(false);
				return true;
			}
			default:
				return false;
			}
		} else {
			return getMultiSpecial(shell, uri, item);
