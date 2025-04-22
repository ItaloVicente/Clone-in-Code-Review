	private Map<String
			throws IOException {
		Map<String

		List<String> wanted = req.getWantedRefs();
		Map<String

		for (String refName : wanted) {
			Ref ref = resolved.get(refName);
			if (ref == null) {
				throw new PackProtocolException(MessageFormat
						.format(JGitText.get().invalidRefName
			}
			ObjectId oid = ref.getObjectId();
			if (oid == null) {
				throw new PackProtocolException(MessageFormat
						.format(JGitText.get().invalidRefName
			}
			result.put(refName
		}
		return result;
	}

