				target = Constants.ORIG_HEAD;
			ObjectId mergeHead = repository.resolve(target);
			if (mergeHead == null)
				throw new IOException(NLS.bind(
						UIText.ValidationUtils_CanNotResolveRefMessage, target));
			return revWalk.parseCommit(mergeHead);
		} catch (IOException e) {
			throw new InvocationTargetException(e);
		}
	}

	private RevCommit getLeftCommit(RevWalk revWalk, Repository repository)
			throws InvocationTargetException {
		try {
			ObjectId head = repository.resolve(Constants.HEAD);
			if (head == null)
				throw new IOException(NLS.bind(
						UIText.ValidationUtils_CanNotResolveRefMessage,
						Constants.HEAD));
			return revWalk.parseCommit(head);
		} catch (IOException e) {
			throw new InvocationTargetException(e);
