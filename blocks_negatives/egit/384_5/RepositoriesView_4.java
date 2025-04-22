		TreeItem[] selectedItems = tv.getTree().getSelection();
		for (TreeItem item : selectedItems) {
			RepositoryTreeNode node = (RepositoryTreeNode) item.getData();
			switch (node.getType()) {
			case PROJ:
				projects.add(node);
				break;
			case REF:
				refs.add(node);
				break;
			case REPO:
				repos.add(node);
				break;
			default:
				break;
			}
		}
