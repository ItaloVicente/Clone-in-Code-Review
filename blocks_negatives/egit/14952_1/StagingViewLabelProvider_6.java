			if (presentation == StagingView.PRESENTATION_COMPRESSED_FOLDERS) {
				if (stagingFolderEntry.getPath().toString().length() <= stagingView
						.getCurrentRepository().getWorkTree().getPath()
						.length() + 1) {
				} else {
					return stagingFolderEntry
							.getPath()
							.toString()
							.substring(
									stagingView.getCurrentRepository()
											.getWorkTree().getPath().length() + 1);
				}
			} else {
				return stagingFolderEntry.getFile().getName();
			}
