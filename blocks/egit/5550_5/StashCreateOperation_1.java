					StashCreateCommand command = Git.wrap(repository).stashCreate();
					if (message != null) {
						command.setIndexMessage(message);
						command.setWorkingDirectoryMessage(message);
					}
					commit = command.call();
