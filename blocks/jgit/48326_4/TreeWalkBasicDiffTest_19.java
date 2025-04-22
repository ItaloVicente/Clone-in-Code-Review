				final Tree root = new Tree(db);
				{
					final Tree subA = root.addTree("sub-a");
					subA.addFile("empty").setId(aFileId);
					subA.setId(inserter.insert(OBJ_TREE
				}
				{
					final Tree subB = root.addTree("sub-b");
					subB.addFile("empty").setId(bFileId);
					subB.setId(inserter.insert(OBJ_TREE
				}
				{
					final Tree subC = root.addTree("sub-c");
					subC.addFile("empty").setId(cFileId2);
					subC.setId(inserter.insert(OBJ_TREE
				}
				newTree = inserter.insert(OBJ_TREE
