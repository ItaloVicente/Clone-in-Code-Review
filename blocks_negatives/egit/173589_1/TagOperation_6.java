		try {
			ObjectId tagId;
			repo.open(startPointRef);
			try (ObjectInserter inserter = repo.newObjectInserter()) {
				tagId = inserter.insert(tag);
				inserter.flush();
