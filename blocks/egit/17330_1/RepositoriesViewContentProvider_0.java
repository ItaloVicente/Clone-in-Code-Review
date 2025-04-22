	private Object[] getTagsChildren(RepositoryTreeNode parentNode,
			Repository repo) {
		List<RepositoryTreeNode<Ref>> nodes = new ArrayList<RepositoryTreeNode<Ref>>();

		RevWalk walk = new RevWalk(repo);
		walk.setRetainBody(true);
		try {
			Map<String, Ref> tagRefs = getRefs(repo, Constants.R_TAGS);
			for (Ref tagRef : tagRefs.values()) {
				ObjectId objectId = tagRef.getLeaf().getObjectId();
				RevObject revObject = walk.parseAny(objectId);
				RevObject peeledObject = walk.peel(revObject);
				TagNode tagNode = createTagNode(parentNode, repo, tagRef,
						revObject, peeledObject);
				nodes.add(tagNode);
			}
		} catch (IOException e) {
			return handleException(e, parentNode);
		} finally {
			walk.release();
		}

		return nodes.toArray();
	}

	private TagNode createTagNode(RepositoryTreeNode parentNode,
			Repository repo, Ref ref, RevObject revObject,
			RevObject peeledObject) {
		boolean annotated = (revObject instanceof RevTag);
		if (peeledObject instanceof RevCommit) {
			RevCommit commit = (RevCommit) peeledObject;
			String id = commit.getId().name();
			String message = commit.getShortMessage();
			return new TagNode(parentNode, repo, ref, annotated, id, message);
		} else {
			return new TagNode(parentNode, repo, ref, annotated, "", ""); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

