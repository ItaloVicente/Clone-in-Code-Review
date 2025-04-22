			BlockList<ObjectToPack>[] lists = objectsLists;
			if (lists != null) {
				objCnt += lists[OBJ_COMMIT].size();
				objCnt += lists[OBJ_TREE].size();
				objCnt += lists[OBJ_BLOB].size();
				objCnt += lists[OBJ_TAG].size();
			}
