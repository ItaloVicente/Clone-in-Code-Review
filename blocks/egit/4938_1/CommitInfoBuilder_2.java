			Ref tag = tagEntry.getValue();
			tag = db.peel(tag);
			Object id = tag.getPeeledObjectId();
			if(id == null)
				id = tag.getObjectId();
			if (commit.equals(id)) {
