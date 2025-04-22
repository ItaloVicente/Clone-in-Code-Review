
		if (modelsToSave.isEmpty()) {
			return true;
		}

		final List finalModels = modelsToSave;
		IRunnableWithProgress progressOp = new IRunnableWithProgress() {
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
				}
				monitorWrap.done();
			}
		};

		return SaveableHelper.runProgressMonitorOperation(WorkbenchMessages.Save_All, progressOp,
				runnableContext, shellProvider);
	}

	private static List convertToSaveables(List parts, boolean closing, boolean addNonPartSources) {
		ArrayList result = new ArrayList();
		HashSet seen = new HashSet();
		for (Iterator i = parts.iterator(); i.hasNext();) {
			IWorkbenchPart part = (IWorkbenchPart) i.next();
			Saveable[] saveables = getSaveables(part);
			for (int j = 0; j < saveables.length; j++) {
				Saveable saveable = saveables[j];
				if (saveable.isDirty() && !seen.contains(saveable)) {
					seen.add(saveable);
					if (!closing
							|| closingLastPartShowingModel(saveable, parts, part.getSite()
									.getPage())) {
						result.add(saveable);
					}
				}
			}
		}
		if (addNonPartSources) {
			SaveablesList saveablesList = (SaveablesList) PlatformUI.getWorkbench().getService(
					ISaveablesLifecycleListener.class);
			ISaveablesSource[] nonPartSources = saveablesList.getNonPartSources();
			for (int i = 0; i < nonPartSources.length; i++) {
				Saveable[] saveables = nonPartSources[i].getSaveables();
				for (int j = 0; j < saveables.length; j++) {
					Saveable saveable = saveables[j];
					if (saveable.isDirty() && !seen.contains(saveable)) {
						seen.add(saveable);
						result.add(saveable);
					}
				}
			}
		}
		return result;
	}

	private static Saveable[] getSaveables(IWorkbenchPart part) {
		if (part instanceof ISaveablesSource) {
			ISaveablesSource source = (ISaveablesSource) part;
			return source.getSaveables();
		}
		return new Saveable[] { new DefaultSaveable(part) };
	}

	private static boolean closingLastPartShowingModel(Saveable model, List closingParts,
			IWorkbenchPage page) {
		HashSet closingPartsWithSameModel = new HashSet();
		for (Iterator i = closingParts.iterator(); i.hasNext();) {
			IWorkbenchPart part = (IWorkbenchPart) i.next();
			Saveable[] models = getSaveables(part);
			if (Arrays.asList(models).contains(model)) {
				closingPartsWithSameModel.add(part);
			}
		}
		IWorkbenchPartReference[] pagePartRefs = ((WorkbenchPage) page).getSortedParts();
		HashSet pagePartsWithSameModels = new HashSet();
		for (int i = 0; i < pagePartRefs.length; i++) {
			IWorkbenchPartReference partRef = pagePartRefs[i];
			IWorkbenchPart part = partRef.getPart(false);
			if (part != null) {
				Saveable[] models = getSaveables(part);
				if (Arrays.asList(models).contains(model)) {
					pagePartsWithSameModels.add(part);
				}
			}
		}
		for (Iterator i = closingPartsWithSameModel.iterator(); i.hasNext();) {
			IWorkbenchPart part = (IWorkbenchPart) i.next();
			pagePartsWithSameModels.remove(part);
		}
		return pagePartsWithSameModels.isEmpty();
