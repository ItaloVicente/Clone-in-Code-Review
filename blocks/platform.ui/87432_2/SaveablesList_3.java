		IRunnableWithProgress progressOp = monitor -> {
			IProgressMonitor monitorWrap = new EventLoopProgressMonitor(monitor);
			SubMonitor subMonitor = SubMonitor.convert(monitorWrap, WorkbenchMessages.Saving_Modifications,
					finalModels.size());
			for (Saveable model : finalModels) {
				if (!model.isDirty()) {
					subMonitor.worked(1);
					continue;
