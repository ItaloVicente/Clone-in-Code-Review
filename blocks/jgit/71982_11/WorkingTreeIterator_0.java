			if (FilterCommandRegistry.isRegistered(filterCommand)) {
				LocalFile buffer = new TemporaryBuffer.LocalFile(null);
				FilterCommand command = FilterCommandRegistry
						.createFilterCommand(filterCommand
								buffer);
				while (command.run() != -1) {
				}
				return buffer.openInputStream();
			}
