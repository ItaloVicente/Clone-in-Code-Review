		} else if (node.getType() == RepositoryTreeNodeType.TAG) {
			lastObject = object;
			checkChangeType(SourceType.TAG);
			lastRepositorySource = new TagPropertySource(node.getRepository(),
					(Ref) node.getObject());
			return lastRepositorySource;
		} else {
