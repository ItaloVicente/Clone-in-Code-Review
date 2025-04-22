	private void formatDiffs(final List<FileDiff> diffs) {
		final Repository repository = fileViewer.getRepository();
		Job formatJob = new Job(UIText.GitHistoryPage_FormatDiffJobName) {
			protected IStatus run(IProgressMonitor monitor) {
				final IDocument document = new Document();
				final DiffStyleRangeFormatter formatter = new DiffStyleRangeFormatter(
						document);

				monitor.beginTask("", diffs.size()); //$NON-NLS-1$
				for (FileDiff diff : diffs) {
					if (monitor.isCanceled())
						break;
					if (diff.getCommit().getParentCount() > 1)
						break;
					monitor.setTaskName(diff.getPath());
					try {
						formatter.write(repository, diff);
					} catch (IOException ignore) {
					}
					monitor.worked(1);
				}
				monitor.done();
				UIJob uiJob = new UIJob(UIText.GitHistoryPage_FormatDiffJobName) {
					public IStatus runInUIThread(IProgressMonitor uiMonitor) {
						if (UIUtils.isUsable(diffViewer)) {
							diffViewer.setDocument(document);
							diffViewer.setFormatter(formatter);
							resizeCommentAndDiffScrolledComposite();
						}
						return Status.OK_STATUS;
					}
				};
				uiJob.schedule();
				return Status.OK_STATUS;
			}
		};
		formatJob.schedule();
	}

	private void setWrap(boolean wrap) {
		commentViewer.getTextWidget().setWordWrap(wrap);
		diffViewer.getTextWidget().setWordWrap(wrap);
		resizeCommentAndDiffScrolledComposite();
	}

	private void resizeCommentAndDiffScrolledComposite() {
		int widthHint;
		if (commentViewer.getTextWidget().getWordWrap()) {
			widthHint = commentAndDiffScrolledComposite.getClientArea().width;
			if (commentAndDiffScrolledComposite.getVerticalBar() != null
					&& !commentAndDiffScrolledComposite.getVerticalBar().isVisible())
				widthHint -= commentAndDiffScrolledComposite.getVerticalBar().getSize().x;
		} else {
			widthHint = SWT.DEFAULT;
		}
		Point size = commentAndDiffComposite
				.computeSize(widthHint, SWT.DEFAULT);
		commentAndDiffComposite.setSize(size);
		commentAndDiffScrolledComposite.setMinSize(size);
	}

