					if (proj.revision == null)
						proj.revision = defaultRevision;
					String url = remoteUrl + proj.name;
					command.addSubmodule(url, proj.path, proj.revision);
					for (CopyFile copyfile : proj.copyfiles) {
						try {
							copyfile.copy();
						} catch (IOException e) {
							throw new SAXException(
									RepoText.get().copyFileFailed, e);
						}
						AddCommand add = command.git
							.add()
							.addFilepattern(copyfile.relativeDest);
						try {
							add.call();
						} catch (GitAPIException e) {
							throw new SAXException(e);
						}
					}
