		final ObjectId newTree;
		{
			final Tree root = new Tree(db);
			{
				final Tree subA = root.addTree("sub-a");
				subA.addFile("empty").setId(aFileId);
				subA.setId(inserter.insert(OBJ_TREE, subA.format()));
			}
			{
				final Tree subB = root.addTree("sub-b");
				subB.addFile("empty").setId(bFileId);
				subB.setId(inserter.insert(OBJ_TREE, subB.format()));
			}
