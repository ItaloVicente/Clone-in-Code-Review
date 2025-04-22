		List<ObjectId> deepenNots = new ArrayList<>();
		for (String s : req.getDeepenNotRefs()) {
			Ref ref = db.getRefDatabase().getRef(s);
			if (ref == null) {
				throw new PackProtocolException(MessageFormat
						.format(JGitText.get().invalidRefName
			}
			deepenNots.add(ref.getObjectId());
		}

