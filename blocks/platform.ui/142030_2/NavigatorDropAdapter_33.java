			operation.copyResources(sources, target);
		} else {
			ReadOnlyStateChecker checker = new ReadOnlyStateChecker(getShell(),
					ResourceNavigatorMessages.MoveResourceAction_title,
					ResourceNavigatorMessages.MoveResourceAction_checkMoveMessage);
			sources = checker.checkReadOnlyResources(sources);
			MoveFilesAndFoldersOperation operation = new MoveFilesAndFoldersOperation(getShell());
			operation.copyResources(sources, target);
