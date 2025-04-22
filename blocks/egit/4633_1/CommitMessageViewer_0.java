				if (!event.getResult().isOK())
					return;
				final StyledText text = getTextWidget();
				if (text == null || text.isDisposed())
					return;
				final FormatJob job = (FormatJob) event.getJob();
				text.getDisplay().asyncExec(new Runnable() {
					public void run() {
						if (text.isDisposed())
							return;
						setDocument(new Document(job.getFormatResult()
								.getCommitInfo()));
						text.setStyleRanges(job.getFormatResult()
								.getStyleRange());
					}
				});
