		case WORKINGDIR:
			WorkingDirNode workingDir = (WorkingDirNode) node;
			return addConflictDecorationIfNecessary(workingDir.getPath().toFile(), base);
		case FILE:
		case FOLDER:
			File file = (File) node.getObject();
			return addConflictDecorationIfNecessary(file, base);
