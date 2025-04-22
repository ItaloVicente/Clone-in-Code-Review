		try {
			for (Ref tag : getTags()) {
				tag = repository.getRefDatabase().peel(tag);
				ObjectId id = tag.getPeeledObjectId();
				if (id == null) {
					id = tag.getObjectId();
				}
				if (commit.equals(id)) {
					tags.add(tag);
				}
			}
		} catch (IOException e) {
			Activator.logError(MessageFormat.format(
					UIText.CommitEditor_couldNotGetTags,
					commit.getName(),
					repository.getDirectory().getAbsolutePath()), e);
