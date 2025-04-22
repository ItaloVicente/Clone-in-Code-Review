					final RepositoryMapping map = RepositoryMapping
							.getMapping(file);
					final String gitPath = map
							.getRepoRelativePath(file);
					final IFileRevision file1 = GitFileRevision.inCommit(db,
							commit1, gitPath, null);
					final ITypedElement base = SaveableCompareEditorInput.createFileElement(file);

					final FileRevisionTypedElement next = new FileRevisionTypedElement(file1);

