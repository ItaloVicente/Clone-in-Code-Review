		RefDatabase refs = db.getRefDatabase();
		try {
			for (Ref tag : refs.getRefsByPrefix(Constants.R_TAGS)) {
				Ref ref = refs.peel(tag);
				ObjectId refId = ref.getPeeledObjectId();
				if (refId == null)
					refId = ref.getObjectId();
				if (AnyObjectId.equals(refId, commit)) {
					String tagName = tag.getName()
							.substring(Constants.R_TAGS.length());
					ret.add(new GitTag(tagName));
				}
			}
		} catch (IOException e) {
			Activator.logError(MessageFormat.format(
					CoreText.CommitFileRevision_errorLookingUpTags,
					db.getDirectory().getAbsolutePath()), e);
