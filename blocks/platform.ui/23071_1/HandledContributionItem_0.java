			final MCommand command = model.getCommand();
			if (command == null) {
				item.setText("UnLabled"); //$NON-NLS-1$
			} else {
				item.setText(command.getCommandName());
			}
