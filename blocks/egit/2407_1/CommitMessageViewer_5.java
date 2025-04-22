	private Ref getAncestorTag() throws IOException {
		RevWalk revWalk = new RevWalk(db);

		Map<String, Ref> tagsMap = db.getTags();
		Ref tagRef = null;

		for (String tagName : tagsMap.keySet()) {
			RevCommit current = revWalk.parseCommit(commit);
			RevCommit newTag = revWalk.parseCommit(tagsMap.get(tagName).getObjectId());

			if (newTag.getId().equals(commit)) {
				continue;
			}

			if (revWalk.isMergedInto(newTag, current)) {
				if (tagRef != null) {
					RevCommit oldTag = revWalk.parseCommit(tagRef.getObjectId());

					if (revWalk.isMergedInto(oldTag, newTag)) {
						tagRef = tagsMap.get(tagName);
					}
				} else {
					tagRef = tagsMap.get(tagName);
				}
			}

		}


		return tagRef;
	}

	private Ref getChildTag() throws IOException {
		RevWalk revWalk = new RevWalk(db);

		Map<String, Ref> tagsMap = db.getTags();
		Ref tagRef = null;

		for (String tagName : tagsMap.keySet()) {
			RevCommit current = revWalk.parseCommit(commit);
			RevCommit newTag = revWalk.parseCommit(tagsMap.get(tagName).getObjectId());

			if (newTag.getId().equals(commit)) {
				continue;
			}

			if (revWalk.isMergedInto(current, newTag)) {
				if (tagRef != null) {
					RevCommit oldTag = revWalk.parseCommit(tagRef.getObjectId());

					if (revWalk.isMergedInto(newTag, oldTag)) {
						tagRef = tagsMap.get(tagName);
					}
				} else {
					tagRef = tagsMap.get(tagName);
				}
			}

		}

		return tagRef;
	}

	private List<Ref> getBranches() {
		RevWalk revWalk = new RevWalk(db);
		List<Ref> result = new ArrayList<Ref>();

		try {
			Map<String, Ref> refsMap = new HashMap<String, Ref>();
			refsMap.putAll(db.getRefDatabase().getRefs(Constants.R_HEADS));
			refsMap.putAll(db.getRefDatabase().getRefs(Constants.R_REMOTES));

			for (String headName : refsMap.keySet()) {
				RevCommit headCommit = revWalk.parseCommit(refsMap.get(headName).getObjectId());
				RevCommit base = revWalk.parseCommit(commit);

				if (revWalk.isMergedInto(base, headCommit)) {
					result.add(refsMap.get(headName));
				}
			}
		} catch (IOException e) {
		}

		return result;
	}

