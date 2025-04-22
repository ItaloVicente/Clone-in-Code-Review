	private String getHeadName(Ref head) {
		String headName;
		if (head.isSymbolic())
			headName = head.getTarget().getName();
		else
			headName = head.getObjectId().getName();
		return headName;
	}

	private Ref getHead() throws IOException
		Ref head = repo.getRef(Constants.HEAD);
		if (head == null || head.getObjectId() == null)
			throw new RefNotFoundException(MessageFormat.format(
					JGitText.get().refNotResolved
		return head;
	}

