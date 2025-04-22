				Supplier<ITypedElement> leftItem;
				if (leftIter instanceof WorkingTreeIterator) {
					leftItem = () -> {
						Path repositoryPath = new Path(
								repo.getWorkTree().getAbsolutePath());
						IPath location = repositoryPath.append(gitPath);
						assert location != null;
						IFile file = ResourceUtil.getFileForLocation(location,
								false);
						LocalResourceTypedElement item;
						if (file != null) {
							item = new LocalResourceTypedElement(file);
						} else {
							item = new LocalNonWorkspaceTypedElement(repo,
									location);
						}
						item.setSharedDocumentListener(
								new LocalResourceSaver(item));
						return item;
					};
				} else {
					leftItem = () -> {
						data.fill(repo, tw, gitPath);
						GitFileRevision revision = GitFileRevision.inCommit(
								repo, leftCommit, gitPath,
								leftIter.getEntryObjectId(),
								data.getMetadata());
						return new FileRevisionTypedElement(revision,
								data.getEncoding());
					};
				}
