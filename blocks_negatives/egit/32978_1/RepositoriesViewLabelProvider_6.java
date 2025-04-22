				return styled;
			case TAG:
				return getStyledTextForTag((TagNode) node);
			case STASHED_COMMIT:
				return getStyledTextForCommit((StashedCommitNode) node);
			case PUSH:
			case FETCH:
			case FILE:
			case FOLDER:
			case BRANCHES:
			case LOCAL:
			case REMOTETRACKING:
			case BRANCHHIERARCHY:
			case TAGS:
			case ADDITIONALREFS:
			case REMOTES:
			case REMOTE:
			case SUBMODULES:
			case STASH:
			case ERROR: {
				String label = getSimpleText(node);
				if (label != null)
					return new StyledString(label);
