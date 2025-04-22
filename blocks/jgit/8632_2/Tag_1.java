			try {
				command.call();
			} catch (RefAlreadyExistsException e) {
				throw die(MessageFormat.format(CLIText.get().tagAlreadyExists
						tagName));
			}
