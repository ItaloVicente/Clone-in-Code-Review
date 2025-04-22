		List<ObjectId> deepenNots = new ArrayList<>();
		for (String s : req.getDeepenNotRefs()) {
			Ref ref = findRef(s);
			if (ref == null) {
				throw new PackProtocolException(MessageFormat
						.format(JGitText.get().invalidRefName, s));
			}
			deepenNots.add(ref.getObjectId());
		}
