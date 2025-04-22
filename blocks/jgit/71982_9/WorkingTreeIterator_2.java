			if (Repository.isRegistered(filterCommand)) {
				LocalFile buffer = new TemporaryBuffer.LocalFile(null);
				FilterCommand command = Repository.getFilterCommand(filterCommand
						repository
				while (command.run() != -1) {
				}
				return buffer.openInputStream();
			}
