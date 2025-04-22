		case STASH:
			return UIText.RepositoriesViewLabelProvider_StashNodeText;
		case STASHED_COMMIT:
			return MessageFormat.format(
					"{0}@'{'{1}'}'", //$NON-NLS-1$
					Constants.STASH,
					Integer.valueOf(((StashedCommitNode) node).getIndex()));
