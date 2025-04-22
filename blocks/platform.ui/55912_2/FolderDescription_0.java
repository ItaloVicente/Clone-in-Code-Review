		SubMonitor subMonitor = SubMonitor.convert(mon, 300);
		subMonitor.setTaskName(UndoMessages.FolderDescription_NewFolderProgress);
		if (subMonitor.isCanceled()) {
			throw new OperationCanceledException();
		}
		if (filters != null) {
			SubMonitor loopMonitor = subMonitor.newChild(100).setWorkRemaining(filters.length);
			for (int i = 0; i < filters.length; i++) {
				folderHandle.createFilter(filters[i].getType(), filters[i].getFileInfoMatcherDescription(), 0,
						loopMonitor.newChild(1));
