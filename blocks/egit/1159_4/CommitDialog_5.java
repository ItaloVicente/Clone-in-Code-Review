				public void checkStateChanged(CheckStateChangedEvent event) {
				       if( !event.getChecked() ) {
				    	   filesViewer.setAllChecked(true);
				       }
				}
			});
			filesViewer.setAllGrayed(true);
			filesViewer.setAllChecked(true);
		}
		else {
			for (IFile selectedFile : preselectedFiles) {
				for (CommitItem item : items) {
					if (item.file.equals(selectedFile)) {
						filesViewer.setChecked(item, true);
						break;
					}
