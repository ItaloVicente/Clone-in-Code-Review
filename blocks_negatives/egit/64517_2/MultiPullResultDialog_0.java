	private final static class LabelProvider extends BaseLabelProvider
			implements ITableLabelProvider {
		private final RepositoryUtil utils = Activator.getDefault()
				.getRepositoryUtil();

		@Override
		@SuppressWarnings("unchecked")
		public Image getColumnImage(Object element, int columnIndex) {
			if (columnIndex != 3)
				return null;
			Entry<Repository, Object> item = (Entry<Repository, Object>) element;
			Object resultOrError = item.getValue();
			if (resultOrError instanceof IStatus)
				return PlatformUI.getWorkbench().getSharedImages().getImage(
						ISharedImages.IMG_ELCL_STOP);

			PullResult res = (PullResult) item.getValue();
			boolean success = res.isSuccessful();
			if (!success)
				return PlatformUI.getWorkbench().getSharedImages().getImage(
						ISharedImages.IMG_ELCL_STOP);
			return null;
		}

		@Override
		@SuppressWarnings("unchecked")
		public String getColumnText(Object element, int columnIndex) {
			Entry<Repository, Object> item = (Entry<Repository, Object>) element;
			switch (columnIndex) {
			case 0:
				return utils.getRepositoryName(item.getKey());
			case 1: {
				if (item.getValue() instanceof IStatus)
					return UIText.MultiPullResultDialog_UnknownStatus;
				PullResult pullRes = (PullResult) item.getValue();
				if (pullRes.getFetchResult() == null)
					return UIText.MultiPullResultDialog_NothingFetchedStatus;
				else if (pullRes.getFetchResult().getTrackingRefUpdates()
						.isEmpty())
					return UIText.MultiPullResultDialog_NothingUpdatedStatus;
				else {
					int updated = pullRes.getFetchResult()
							.getTrackingRefUpdates().size();
					if ( updated == 1)
						return UIText.MultiPullResultDialog_UpdatedOneMessage;
					return NLS.bind(
							UIText.MultiPullResultDialog_UpdatedMessage,
							Integer.valueOf(updated));
				}
			}
			case 2: {
				if (item.getValue() instanceof IStatus)
					return UIText.MultiPullResultDialog_UnknownStatus;
				PullResult pullRes = (PullResult) item.getValue();
				if (pullRes.getMergeResult() != null) {
					return NLS.bind(
							UIText.MultiPullResultDialog_MergeResultMessage,
							MergeResultDialog.getStatusText(pullRes
									.getMergeResult().getMergeStatus()));
				} else if (pullRes.getRebaseResult() != null) {
					RebaseResult res = pullRes.getRebaseResult();
					return NLS.bind(
							UIText.MultiPullResultDialog_RebaseResultMessage,
							RebaseResultDialog.getStatusText(res.getStatus()));
				} else {
					return UIText.MultiPullResultDialog_NothingUpdatedStatus;
				}
			}
			case 3:
				if (item.getValue() instanceof IStatus) {
					IStatus status = (IStatus) item.getValue();
					return status.getMessage();
				}
				PullResult res = (PullResult) item.getValue();
				if (res.isSuccessful())
					return UIText.MultiPullResultDialog_OkStatus;
				else
					return UIText.MultiPullResultDialog_FailedStatus;
			default:
				return null;
			}
		}
	}
