				Supplier<ITypedElement> leftItem = () -> {
					data.fill(repo, tw, gitPath);
					GitFileRevision revision = GitFileRevision.inCommit(repo,
							leftCommit, gitPath,
							leftIter.getEntryObjectId(), data.getMetadata());
					return new FileRevisionTypedElement(revision,
							data.getEncoding());
				};
