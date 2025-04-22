		Map<String
		for (String refName : req.getWantedRefs()) {
			Ref ref = getRef(refName);
			if (ref == null) {
				throw new PackProtocolException(MessageFormat
						.format(JGitText.get().invalidRefName
			}
			ObjectId oid = ref.getObjectId();
			if (oid == null) {
				throw new PackProtocolException(MessageFormat
						.format(JGitText.get().invalidRefName
			}
			req.getWantIds().add(oid);
			wantedRefs.put(refName
		}
		wantIds = req.getWantIds();

