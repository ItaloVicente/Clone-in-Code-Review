		for (Ref tag : getTags()) {
			tag = repository.peel(tag);
			ObjectId id = tag.getPeeledObjectId();
			if (id == null)
				id = tag.getObjectId();
			if (!commit.equals(id))
				continue;
			tags.add(tag);
