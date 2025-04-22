				if (selectedResources.length == 0) {
					message = WorkbenchNavigatorMessages.DropAdapter_dropOperationErrorOther;
				} else {
					CopyFilesAndFoldersOperation operation;
					if (aDropOperation == DND.DROP_COPY) {
						if (Policy.DEBUG_DND) {
							System.out
						}

						operation = new CopyFilesAndFoldersOperation(getShell());
					} else {
						if (Policy.DEBUG_DND) {
							System.out
						}
						operation = new MoveFilesAndFoldersOperation(getShell());
