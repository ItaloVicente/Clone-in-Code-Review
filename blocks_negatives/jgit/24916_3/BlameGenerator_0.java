		for (int pIdx = 0; pIdx < pCnt; pIdx++) {
			RevCommit parent = n.getParent(pIdx);
			if (parent.has(SEEN))
				continue;
			revPool.parseHeaders(parent);
		}

