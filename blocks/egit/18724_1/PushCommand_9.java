	private PushTagsWizard createPushTagsWizard(List<RepositoryTreeNode> nodes) {
		List<String> tagNames = new ArrayList<String>();
		for (RepositoryTreeNode node : nodes) {
			if (node instanceof TagNode) {
				TagNode tagNode = (TagNode) node;
				tagNames.add(tagNode.getObject().getName());
			}
		}
		Repository repository = nodes.get(0).getRepository();
		return new PushTagsWizard(repository, tagNames);
	}

