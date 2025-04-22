			final RevCommit headCommit;
			try {
				ObjectId head = repo.resolve(Constants.HEAD);
				if (head == null)
					throw new IOException(NLS.bind(
							UIText.ValidationUtils_CanNotResolveRefMessage,
							Constants.HEAD));
				headCommit = rw.parseCommit(head);
			} catch (IOException e) {
				throw new InvocationTargetException(e);
