		case TAG: {
			Ref ref = (Ref) node.getObject();
			String refName = node.getRepository().shortenRefName(ref.getName());
			if (node.getParent().getType() == RepositoryTreeNodeType.BRANCHHIERARCHY) {
				int index = refName.lastIndexOf('/');
				refName = refName.substring(index + 1);
			}
			return refName;
		}
		case ADDITIONALREF: {
