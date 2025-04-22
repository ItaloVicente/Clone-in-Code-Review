				AbstractTreeIterator leftIter = tw.getTree(leftIndex,
						AbstractTreeIterator.class);
				AbstractTreeIterator rightIter = tw.getTree(rightIndex,
						AbstractTreeIterator.class);
				data.clear();

				String gitPath = tw.getPathString();

				Supplier<ITypedElement> leftItem = () -> {
					data.fill(repo, tw, gitPath);
					GitFileRevision revision = GitFileRevision.inCommit(repo,
							leftCommit, gitPath,
							leftIter.getEntryObjectId(), data.getMetadata());
					return new FileRevisionTypedElement(revision,
							data.getEncoding());
				};
				Supplier<ITypedElement> rightItem = () -> {
					data.fill(repo, tw, gitPath);
					GitFileRevision revision = GitFileRevision.inCommit(repo,
							rightCommit, gitPath,
							rightIter.getEntryObjectId(), data.getMetadata());
					return new FileRevisionTypedElement(revision,
							data.getEncoding());
				};
