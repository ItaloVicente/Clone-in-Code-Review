			for (IFile selectedFile : preselectedFiles) {
				for (CommitItem item : items) {
					if (item.file.equals(selectedFile) &&
							item.status != Status.UNTRACKED &&
							item.status != Status.ASSUME_UNCHANGED) {
						filesViewer.setChecked(item, true);
						break;
					}
