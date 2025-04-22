			{
				final Tree subC = root.addTree("sub-c");
				subC.addFile("empty").setId(cFileId1);
				subC.setId(inserter.insert(OBJ_TREE, subC.format()));
			}
			oldTree = inserter.insert(OBJ_TREE, root.format());
		}
