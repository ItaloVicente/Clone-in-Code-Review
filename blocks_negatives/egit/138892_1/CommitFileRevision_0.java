		for (final Map.Entry<String, Ref> tag : db.getTags().entrySet()) {
			Ref ref = db.peel(tag.getValue());
			ObjectId refId = ref.getPeeledObjectId();
			if (refId == null)
				refId = ref.getObjectId();
			if (!AnyObjectId.equals(refId, commit))
				continue;
			ret.add(new GitTag(tag.getKey()));
