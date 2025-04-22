		case SMUDGED:
			return contentCheck(entry);
		case EQUAL:
			return false;
		case DIFFER_BY_METADATA:
			return true;
		default:
			throw new IllegalStateException(MessageFormat.format(
					JGitText.get().unexpectedCompareResult
