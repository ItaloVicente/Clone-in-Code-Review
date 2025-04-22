	public int getVbucket() {
		return vbucket;
	}

	public void setVBucket(int vbucket) {
		this.vbucket = vbucket;
	}

	public Collection<MemcachedNode> getNotMyVbucketNodes() {
		return notMyVbucketNodes;
	}

	public void addNotMyVbucketNode(MemcachedNode node) {
		notMyVbucketNodes.add(node);
	}

	public void setNotMyVbucketNodes(Collection<MemcachedNode> nodes) {
		notMyVbucketNodes = nodes;
	}

