	boolean parseInGraph(RevWalk walk) {
		CommitGraph graph = walk.getCommitGraph();
		if (graph == null) {
			return false;
		}
		CommitGraph.CommitData data;
		if (graphPosition >= 0) {
			data = graph.getCommitData(graphPosition);
		} else {
			data = graph.getCommitData(this);
		}
		if (data == null) {
			return false;
		}

		this.tree = walk.lookupTree(data.getTree());
		this.commitTime = (int) data.getCommitTime();
		this.generation = data.getGeneration();

		int[] pGraphList = data.getParents();
		RevCommit[] pList = new RevCommit[pGraphList.length];
		for (int i = 0; i < pList.length; i++) {
			ObjectId objId = graph.getObjectId(pGraphList[i]);
			pList[i] = walk.lookupCommit(objId);
			pList[i].graphPosition = pGraphList[i];
		}
		this.parents = pList;

		flags |= PARSED;
		return true;
	}

