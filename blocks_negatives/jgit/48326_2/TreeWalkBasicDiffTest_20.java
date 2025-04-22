			{
				final Tree subC = root.addTree("sub-c");
				subC.addFile("empty").setId(cFileId2);
				subC.setId(inserter.insert(OBJ_TREE, subC.format()));
			}
			newTree = inserter.insert(OBJ_TREE, root.format());
		}
		inserter.flush();
		inserter.release();
