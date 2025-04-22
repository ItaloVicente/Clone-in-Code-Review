		if (op instanceof VBucketAware) {
			VBucketAware vop = (VBucketAware)op;
			if (!vop.getNotMyVbucketNodes().isEmpty()) {
				for (Operation operation : rv) {
					if (operation instanceof VBucketAware) {
						Collection<MemcachedNode> notMyVbucketNodes = vop.getNotMyVbucketNodes();
						((VBucketAware) operation).setNotMyVbucketNodes(notMyVbucketNodes);
					}
				}
			}
		}
