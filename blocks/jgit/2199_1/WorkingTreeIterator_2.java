		case SMUDGED:
			return contentCheck(entry);
		case EQUAL:
			return false;
		case NOT_EQUAL:
			return true;
		default:
			return true;
