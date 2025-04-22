				if (event.getResult().isOK()) {
					if (getTextWidget().isDisposed())
						return;
					final FormatJob job = (FormatJob) event.getJob();
					getTextWidget().getDisplay().asyncExec(new Runnable() {
						public void run() {
							if (getTextWidget().isDisposed())
								return;
							setDocument(new Document(job.getFormatResult().getCommitInfo()));
							getTextWidget().setStyleRanges(job.getFormatResult().getStyleRange());
						}
					});
				}
