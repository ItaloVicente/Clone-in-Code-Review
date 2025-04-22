			Ref tagRef = repo.exactRef(Constants.R_TAGS + tagName);
			if (tagRef != null) {
				ObjectId tagObjectId = tagRef.getObjectId();
				if (tagObjectId != null) {
					try (RevWalk revWalk = new RevWalk(repo)) {
						RevObject tagObject = revWalk.parseAny(tagObjectId);
						if (tagObject.getType() == Constants.OBJ_TAG) {
							setExistingTag(tagObject);
						} else {
							setExistingTag(tagRef);
						}
					}
					return;
