		while (treeWalk.next()) {
			if (path.isDone(treeWalk)) {
				if (treeWalk.getFileMode(0).getObjectType() != OBJ_BLOB)
					return false;
				treeWalk.getObjectId(idBuf, 0);
				return true;
			}

			if (treeWalk.isSubtree())
				treeWalk.enterSubtree();
