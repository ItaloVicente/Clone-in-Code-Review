			if (Repository.isRegistered(filterCommand)) {
				LocalFile buffer = new TemporaryBuffer.LocalFile(null);
				FilterCommand command = Repository.getCommand(filterCommand
						repository
				while (command.run() != -1) {
				}
				return buffer.openInputStream();
			}
