						/*
						 * TODO: implement add
						AddCommand addCommand = new Git(repo).add();
						boolean fileAdded = false;
						for (String path : notTracked)
							if (commitFileList.contains(path)) {
								addCommand.addFilepattern(path);
								fileAdded = true;
							}
						if (fileAdded)
