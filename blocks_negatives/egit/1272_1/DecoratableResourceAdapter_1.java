			extractResourceProperties(treeWalk);
			break;
		case IResource.PROJECT:
			tracked = true;
		case IResource.FOLDER:
			extractContainerProperties(treeWalk);
			break;
