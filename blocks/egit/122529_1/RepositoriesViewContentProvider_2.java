			return !repo.isBare() && hasDirectoryChildren(repo.getWorkTree());
		case FOLDER:
			return !repo.isBare()
					&& hasDirectoryChildren((File) node.getObject());
		case FILE:
			return false;
