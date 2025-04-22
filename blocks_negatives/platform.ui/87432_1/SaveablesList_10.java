		IRunnableWithProgress progressOp = new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor) {
				IProgressMonitor monitorWrap = new EventLoopProgressMonitor(monitor);
				SubMonitor subMonitor = SubMonitor.convert(monitorWrap, WorkbenchMessages.Saving_Modifications,
						finalModels.size());
				for (Saveable model : finalModels) {
					if (!model.isDirty()) {
						subMonitor.worked(1);
						continue;
					}
					SaveableHelper.doSaveModel(model, subMonitor.split(1),
							shellProvider, blockUntilSaved);
					if (subMonitor.isCanceled())
						break;
