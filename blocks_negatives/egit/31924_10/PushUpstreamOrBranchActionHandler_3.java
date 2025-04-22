	private static Ref getHeadIfSymbolic(Repository repository) {
		try {
			Ref head = repository.getRef(Constants.HEAD);
			if (head != null && head.isSymbolic())
				return head;
			else
				return null;
		} catch (IOException e) {
			return null;
