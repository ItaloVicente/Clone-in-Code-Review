				Supplier<ITypedElement> rightItem = () -> {
					assert rightIter != null;
					data.fill(repo, tw, gitPath);
					GitFileRevision revision = GitFileRevision.inCommit(repo,
							rightCommit, gitPath,
							rightIter.getEntryObjectId(), data.getMetadata());
					return new FileRevisionTypedElement(revision,
							data.getEncoding());
				};
