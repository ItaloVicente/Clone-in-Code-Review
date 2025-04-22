		Map<String, ObjectId> wantedRefs = new TreeMap<>();
		for (String refName : req.getWantedRefs()) {
			Ref ref = getRef(refName);
			if (ref == null) {
				throw new PackProtocolException(MessageFormat
						.format(JGitText.get().invalidRefName, refName));
			}
			ObjectId oid = ref.getObjectId();
			if (oid == null) {
				throw new PackProtocolException(MessageFormat
						.format(JGitText.get().invalidRefName, refName));
			}
			req.getWantIds().add(oid);
			wantedRefs.put(refName, oid);
		}
