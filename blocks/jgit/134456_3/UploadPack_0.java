		wantIds.addAll(req.getWantsIds());
		Map<String
		for (String refName : req.getWantedRefs()) {
			Ref ref = db.getRefDatabase().exactRef(refName);
			if (ref == null) {
				throw new PackProtocolException(MessageFormat
						.format(JGitText.get().invalidRefName
			}
			ObjectId oid = ref.getObjectId();
			if (oid == null) {
				throw new PackProtocolException(MessageFormat
						.format(JGitText.get().invalidRefName
			}
			wantIds.add(oid);
			wantedRefs.put(refName
		}

