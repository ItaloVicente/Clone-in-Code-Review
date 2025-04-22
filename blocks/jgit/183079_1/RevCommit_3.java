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

		int[] pList = data.getParents();
		RevCommit[] parents = new RevCommit[pList.length];
		for (int i = 0; i < pList.length; i ++) {
			ObjectId objId = graph.getObjectId(pList[i]);
			parents[i] = walk.lookupCommit(objId);
			parents[i].graphPosition = pList[i];
		}
		this.parents = parents;

		flags |= PARSED;
		return true;
	}

