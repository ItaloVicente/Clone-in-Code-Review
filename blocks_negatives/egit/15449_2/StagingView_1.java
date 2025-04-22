			processUnstageSelection(selection, headRev, edit);

			try {
				edit.commit();
			} catch (IOException e) {
				MessageDialog.openError(getSite().getShell(),
						UIText.CommitAction_MergeHeadErrorTitle,
						UIText.CommitAction_ErrorReadingMergeMsg);
			}
		} finally {
			dirCache.unlock();
