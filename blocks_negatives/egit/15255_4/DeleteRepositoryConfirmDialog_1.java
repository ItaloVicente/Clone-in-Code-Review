				shouldDelete = deleteWorkDir.getSelection();
				removeProjects.setEnabled(!shouldDelete);
				if (shouldDelete && numberOfProjects > 0) {
					removeProjects.setSelection(true);
					shouldRemoveProjects = true;
				}
