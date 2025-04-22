			if (head == null || head.getObjectId() == null)
				next = new EmptyTypedElement(""); //$NON-NLS-1$
			else {
				RevWalk rw = new RevWalk(repository);
				RevCommit commit = rw.parseCommit(head.getObjectId());

				next = CompareUtils.getFileRevisionTypedElement(gitPath,
						commit, repository);
			}
