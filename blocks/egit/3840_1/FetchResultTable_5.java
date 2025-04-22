	private class FetchResultAdapter extends WorkbenchAdapter {

		private final TrackingRefUpdate update;

		private Object[] children;

		public FetchResultAdapter(TrackingRefUpdate update) {
			this.update = update;
		}

		public String getLabel(Object object) {
			return getStyledText(object).getString();
		}

		public ImageDescriptor getImageDescriptor(Object object) {
			switch (update.getResult()) {
			case IO_FAILURE:
			case LOCK_FAILURE:
			case REJECTED_CURRENT_BRANCH:
			case REJECTED:
				return PlatformUI.getWorkbench().getSharedImages()
						.getImageDescriptor(ISharedImages.IMG_OBJS_ERROR_TSK);
			case FORCED:
			case RENAMED:
			case FAST_FORWARD:
				if (update.getRemoteName().startsWith(Constants.R_HEADS))
					return UIIcons.BRANCH;
				if (update.getLocalName().startsWith(Constants.R_TAGS))
					return UIIcons.TAG;
				if (update.getLocalName().startsWith(Constants.R_NOTES))
					return UIIcons.NOTE;
				break;
			case NEW:
				if (update.getRemoteName().startsWith(Constants.R_HEADS))
					return UIIcons.CREATE_BRANCH;
				if (update.getLocalName().startsWith(Constants.R_TAGS))
					return UIIcons.CREATE_TAG;
				if (update.getLocalName().startsWith(Constants.R_NOTES))
					return UIIcons.NOTE;
				break;
			default:
				return super.getImageDescriptor(object);
			}
			return super.getImageDescriptor(object);
		}

		private void addCommits(StyledString styled, String separator) {
			styled.append('[', StyledString.DECORATIONS_STYLER);
			styled.append(safeAbbreviate(update.getNewObjectId()),
					StyledString.DECORATIONS_STYLER);
			styled.append(separator, StyledString.DECORATIONS_STYLER);
			styled.append(safeAbbreviate(update.getOldObjectId()),
					StyledString.DECORATIONS_STYLER);
			styled.append(']', StyledString.DECORATIONS_STYLER);

			styled.append(MessageFormat.format(
					UIText.FetchResultTable_CounterCommits,
					Integer.valueOf(getChildren(this).length)),
					StyledString.COUNTER_STYLER);
		}

		public Object[] getChildren(Object object) {
			if (children != null)
				return children;

			switch (update.getResult()) {
			case FORCED:
			case FAST_FORWARD:
				RevWalk walk = new RevWalk(reader);
				try {
					walk.setRetainBody(true);
					walk.markStart(walk.parseCommit(update.getNewObjectId()));
					walk.markUninteresting(walk.parseCommit(update
							.getOldObjectId()));
					List<RepositoryCommit> commits = new ArrayList<RepositoryCommit>();
					for (RevCommit commit : walk)
						commits.add(new RepositoryCommit(repo, commit));
					children = commits.toArray();
					break;
				} catch (IOException e) {
					Activator.logError(
							"Error parsing commits from fetch result", e); //$NON-NLS-1$
				} finally {
					walk.release();
				}
			default:
				children = super.getChildren(object);
			}
			return children;
		}

		public StyledString getStyledText(Object object) {
			StyledString styled = new StyledString();
			final String remote = update.getRemoteName();
			final String local = update.getLocalName();
			styled.append(Repository.shortenRefName(remote));
			styled.append(": ", StyledString.QUALIFIER_STYLER); //$NON-NLS-1$
			styled.append(Repository.shortenRefName(local),
					StyledString.QUALIFIER_STYLER);
			styled.append(' ');
			switch (update.getResult()) {
			case LOCK_FAILURE:
				styled.append(UIText.FetchResultTable_statusLockFailure,
						StyledString.DECORATIONS_STYLER);
				break;
			case IO_FAILURE:
				styled.append(UIText.FetchResultTable_statusIOError,
						StyledString.DECORATIONS_STYLER);
				break;
			case NEW:
				if (remote.startsWith(Constants.R_HEADS))
					styled.append(UIText.FetchResultTable_statusNewBranch,
							StyledString.DECORATIONS_STYLER);
				else if (local.startsWith(Constants.R_TAGS))
					styled.append(UIText.FetchResultTable_statusNewTag,
							StyledString.DECORATIONS_STYLER);
				else
					styled.append(UIText.FetchResultTable_statusNew,
							StyledString.DECORATIONS_STYLER);
				break;
			case FORCED:
				addCommits(styled, "..."); //$NON-NLS-1$
				break;
			case FAST_FORWARD:
				addCommits(styled, ".."); //$NON-NLS-1$
				break;
			case REJECTED:
				styled.append(UIText.FetchResultTable_statusRejected,
						StyledString.DECORATIONS_STYLER);
				break;
			case NO_CHANGE:
				styled.append(UIText.FetchResultTable_statusUpToDate,
						StyledString.DECORATIONS_STYLER);
				break;
			default:
				break;
			}
			return styled;
		}
	}
