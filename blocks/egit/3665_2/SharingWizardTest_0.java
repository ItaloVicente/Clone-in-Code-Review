				if (!(gitDirParent.toString() + File.separator)
						.startsWith(workspacePath.toString() + File.separator))
					if (!(gitDirParent.toString() + File.separator)
							.startsWith(getTestDirectory().getCanonicalPath()
									.toString() + File.separator))
						fail("Attempting cleanup of directory neither in workspace nor test directory"
								+ canonicalFile);
				new DisconnectProviderOperation(Collections.singleton(project))
						.execute(null);
