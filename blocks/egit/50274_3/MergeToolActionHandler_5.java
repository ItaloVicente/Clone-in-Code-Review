					if (addFile) {
						System.out.println("addFile: " //$NON-NLS-1$
								+ mergedFileName);
						AddCommand addCommand = new Git(repo).add();
						boolean fileAdded = false;
						for (String path : notTracked)
							if (commitFileList.contains(path)) {
								addCommand.addFilepattern(path);
								fileAdded = true;
							}
						if (fileAdded)
							try {
								addCommand.call();
							} catch (Exception e) {
								throw new CoreException(Activator
										.error(e.getMessage(), e));
							}
					}
