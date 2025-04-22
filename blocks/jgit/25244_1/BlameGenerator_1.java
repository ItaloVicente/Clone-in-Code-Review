	private boolean blameEntireRegionOnParent(Candidate n
		n.sourceCommit = parent;
		push(n);
		return false;
	}

	private boolean splitBlameWithParent(Candidate n
			throws IOException {
		Candidate next = n.create(parent
		next.sourceBlob = idBuf.toObjectId();
		next.loadText(reader);
		return split(next
	}

