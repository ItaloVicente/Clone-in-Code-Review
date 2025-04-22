			for (IFile selectedFile : preselectedFiles) {
				for (CommitItem item : items) {
					if (item.file.equals(selectedFile) &&
							!item.status.equals(UIText.CommitDialog_StatusUntracked) &&
							!item.status.equals(UIText.CommitDialog_StatusAssumeUnchaged)) {
						filesViewer.setChecked(item, true);
						break;
					}
