				if (nestedException != null) {
					ErrorDialog.openError(getView().getShell(),
							BookmarkMessages.OpenBookmark_errorTitle,
							e.getMessage(), nestedException.getStatus());
				} else {
					MessageDialog.openError(getView().getShell(),
							BookmarkMessages.OpenBookmark_errorTitle,
							e.getMessage());
				}
			}
		}
	}
