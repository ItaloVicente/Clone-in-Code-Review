				final Tree root = new Tree(db);
				{
					final Tree subA = root.addTree("sub-a");
					subA.addFile("empty").setId(aFileId);
					subA.setId(inserter.insert(OBJ_TREE
				}
				{
					final Tree subC = root.addTree("sub-c");
					subC.addFile("empty").setId(cFileId1);
					subC.setId(inserter.insert(OBJ_TREE
				}
				oldTree = inserter.insert(OBJ_TREE
