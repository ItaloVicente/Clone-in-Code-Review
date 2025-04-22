			long objCnt = 0;
			objCnt += objectsLists[Constants.OBJ_COMMIT].size();
			objCnt += objectsLists[Constants.OBJ_TREE].size();
			objCnt += objectsLists[Constants.OBJ_BLOB].size();
			objCnt += objectsLists[Constants.OBJ_TAG].size();

			long bytesUsed = OBJECT_TO_PACK_SIZE * objCnt;
			PackingPhase curr = phase;
			if (curr == PackingPhase.COMPRESSING)
				bytesUsed += totalDeltaSearchBytes;
			return new State(curr
