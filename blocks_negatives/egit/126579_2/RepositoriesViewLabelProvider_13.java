		case WORKINGDIR:
					+ node.getRepository().getWorkTree().getAbsolutePath();
		case REMOTE:
		case PUSH:
		case FETCH:
		case ERROR:
			return (String) node.getObject();
