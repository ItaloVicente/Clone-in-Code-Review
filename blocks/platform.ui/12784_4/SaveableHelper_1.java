				try {
					for (Iterator i = dirtyModels.iterator(); i.hasNext();) {
						Saveable model = (Saveable) i.next();
						if (!model.isDirty()) {
							monitor.worked(1);
							continue;
						}
						doSaveModel(model, new SubProgressMonitor(monitorWrap, 1), window, confirm);
						if (monitor.isCanceled()) {
							break;
						}
