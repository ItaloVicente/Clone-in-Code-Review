		RevCommit commit = getCommit().getRevCommit();
		Repository repository = getCommit().getRepository();
		for (Ref tag : getTags()) {
			if (!tag.isPeeled())
				tag = repository.peel(tag);
			ObjectId id = tag.getPeeledObjectId();
			boolean annotated = id != null;
			if (id == null)
				id = tag.getObjectId();
			if (!commit.equals(id))
				continue;
