	private List<ObjectId> parseDeepenNots(List<String> deepenNots) throws IOException {
		List<ObjectId> result = new ArrayList<>();
		for (String s : deepenNots) {
			if (ObjectId.isId(s)) {
				result.add(ObjectId.fromString(s));
			} else {
				Ref ref = findRef(s);
				if (ref == null) {
					throw new PackProtocolException(MessageFormat
															.format(JGitText.get().invalidRefName
				}
				result.add(ref.getObjectId());
			}
		}
		return result;
	}

