		IRunnableWithProgress progressOp = monitor -> {
			IProgressMonitor monitorWrap = new EventLoopProgressMonitor(monitor);
			SubMonitor subMonitor = SubMonitor.convert(monitorWrap, WorkbenchMessages.Save, dirtyModels.size());
			try {
				for (Iterator<Saveable> i = dirtyModels.iterator(); i.hasNext();) {
					Saveable model = i.next();
					if (!model.isDirty()) {
						subMonitor.worked(1);
						continue;
					}
					doSaveModel(model, subMonitor.split(1), window, confirm);
					if (subMonitor.isCanceled()) {
						break;
