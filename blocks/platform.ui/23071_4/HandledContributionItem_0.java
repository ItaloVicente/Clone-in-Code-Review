		String text = model.getLocalizedLabel();

		if (item.getImage() == null || model.getTags().contains(FORCE_TEXT)) {
			if (text == null || text.length() == 0) {
				final MCommand command = model.getCommand();
				if (command == null) {
					item.setText("UnLabled"); //$NON-NLS-1$
				} else {
					item.setText(command.getCommandName());
				}
			} else {
				item.setText(text);
			}
