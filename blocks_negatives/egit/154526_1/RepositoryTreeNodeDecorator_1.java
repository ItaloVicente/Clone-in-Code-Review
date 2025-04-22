		case TAG:
		case ADDITIONALREF:
		case REF:
			String refName = ((Ref) node.getObject()).getName();
			Ref leaf = ((Ref) node.getObject()).getLeaf();

			String compareString = null;
