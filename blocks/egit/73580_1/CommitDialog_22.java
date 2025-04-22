				try (Git git = new Git(repository)) {
					AddCommand addCommand = git.add();
					for (Iterator<?> it = selection.iterator(); it.hasNext();) {
						CommitItem commitItem = (CommitItem) it.next();
						addCommand.addFilepattern(commitItem.path);
					}
					try {
						addCommand.call();
					} catch (Exception e) {
						Activator.logError(UIText.CommitDialog_ErrorAddingFiles,
								e);
					}
