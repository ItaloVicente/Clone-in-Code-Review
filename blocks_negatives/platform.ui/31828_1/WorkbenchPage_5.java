		final List finalModels = modelsToSave;
		IRunnableWithProgress progressOp = new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor) {
				IProgressMonitor monitorWrap = new EventLoopProgressMonitor(monitor);
				monitorWrap.beginTask(WorkbenchMessages.Saving_Modifications, finalModels.size());
				for (Iterator i = finalModels.iterator(); i.hasNext();) {
					Saveable model = (Saveable) i.next();
					if (!model.isDirty()) {
						monitor.worked(1);
						continue;
					}
					SaveableHelper.doSaveModel(model, new SubProgressMonitor(monitorWrap, 1),
							shellProvider, closing || confirm);
					if (monitorWrap.isCanceled()) {
						break;
					}
