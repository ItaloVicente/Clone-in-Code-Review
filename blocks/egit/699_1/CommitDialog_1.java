		for (IFile selectedFile : preselectedFiles) {
			for (CommitItem item : items) {
				if (item.file.equals(selectedFile)) {
					filesViewer.setChecked(item, true);
					break;
				}
			}
		}

