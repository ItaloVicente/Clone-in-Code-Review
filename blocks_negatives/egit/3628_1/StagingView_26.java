			}
		} catch (FileNotFoundException e) {
			MessageDialog.openError(getSite().getShell(),
					UIText.CommitAction_MergeHeadErrorTitle,
					UIText.CommitAction_MergeHeadErrorMessage);
			throw new IllegalStateException(e);
		}
	}
