				inTraverseReturnEvent = true;
				try {
					updateProjectsList(archivePathField.getText().trim());
				} finally {
					inTraverseReturnEvent = false;
				}
