				inTraverseReturnEvent = true;
				try {
					updateProjectsList(directoryPathField.getText().trim());
				} finally {
					inTraverseReturnEvent = false;
				}
