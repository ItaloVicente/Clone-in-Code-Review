		tc = new TableViewerColumn(tv, SWT.NONE);
		col = tc.getColumn();
		tc.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public Image getImage(Object element) {
				@SuppressWarnings("unchecked")
				Entry<Repository, Object> item = (Entry<Repository, Object>) element;
				Object resultOrError = item.getValue();
				if (resultOrError instanceof IStatus) {
					return PlatformUI.getWorkbench().getSharedImages()
							.getImage(ISharedImages.IMG_ELCL_STOP);
				}
				PullResult res = (PullResult) item.getValue();
				boolean success = res.isSuccessful();
				if (!success) {
					return PlatformUI.getWorkbench().getSharedImages()
							.getImage(ISharedImages.IMG_ELCL_STOP);
				}
				return null;
			}

			@Override
			public String getText(Object element) {
				@SuppressWarnings("unchecked")
				Entry<Repository, Object> item = (Entry<Repository, Object>) element;
				if (item.getValue() instanceof IStatus) {
					IStatus status = (IStatus) item.getValue();
					return status.getMessage();
				}
				PullResult res = (PullResult) item.getValue();
				if (res.isSuccessful()) {
					return UIText.MultiPullResultDialog_OkStatus;
				} else {
					return UIText.MultiPullResultDialog_FailedStatus;
				}
			}
		});
