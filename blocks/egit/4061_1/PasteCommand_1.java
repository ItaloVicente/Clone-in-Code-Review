				URIish cloneURI = getCloneURI(content);
				if (cloneURI == null) {
					errorMessage = UIText.RepositoriesView_ClipboardContentNotDirectoryOrURIMessage;
					return null;
				} else {
					CloneCommand cmd = new CloneCommand(cloneURI.toString());
					cmd.execute(event);
					return null;
				}
