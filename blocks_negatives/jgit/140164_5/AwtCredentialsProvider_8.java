		if (items.length == 0) {
			return true;

		} else if (items.length == 1) {
			final CredentialItem item = items[0];

			if (item instanceof CredentialItem.InformationalMessage) {
				JOptionPane.showMessageDialog(null, item.getPromptText(),
						UIText.get().warning, JOptionPane.INFORMATION_MESSAGE);
				return true;

			} else if (item instanceof CredentialItem.YesNoType) {
				CredentialItem.YesNoType v = (CredentialItem.YesNoType) item;
				int r = JOptionPane.showConfirmDialog(null, v.getPromptText(),
						UIText.get().warning, JOptionPane.YES_NO_OPTION);
				switch (r) {
				case JOptionPane.YES_OPTION:
					v.setValue(true);
					return true;

				case JOptionPane.NO_OPTION:
					v.setValue(false);
					return true;

				case JOptionPane.CANCEL_OPTION:
				case JOptionPane.CLOSED_OPTION:
				default:
					return false;
				}

			} else {
				return interactive(uri, items);
			}

		} else {
			return interactive(uri, items);
		}
