					for (CopyFile copyfile : proj.copyfiles) {
						try {
							copyfile.copy();
						} catch (IOException e) {
							throw new SAXException(
									RepoText.get().copyFileFailed
						}
						AddCommand add = new AddCommand(command.repo)
							.addFilepattern(copyfile.relativeDest);
						try {
							add.call();
						} catch (GitAPIException e) {
							throw new SAXException(e);
						}
					}
