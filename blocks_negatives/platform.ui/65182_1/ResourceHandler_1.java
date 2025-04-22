		if (deltaRestore && saveAndRestore && !clearPersistedState) {
			File baseLocation = getBaseLocation();
			File deltaFile = new File(baseLocation, "deltas.xml"); //$NON-NLS-1$

			if (deltaFile.exists()) {
				MApplication appElement = null;
				try {
					File workbenchData = getWorkbenchSaveLocation();
					URI restoreLocationNew = URI.createFileURI(workbenchData.getAbsolutePath());
					resource = resourceSetImpl.createResource(restoreLocationNew);

					Resource oldResource = loadResource(applicationDefinitionInstance);
					appElement = (MApplication) oldResource.getContents().get(0);

					context.set(MApplication.class, appElement);
					ModelAssembler contribProcessor = ContextInjectionFactory.make(
							ModelAssembler.class, context);
					contribProcessor.processModel(true);

					File deltaOldFile = new File(baseLocation, "deltas_42M7migration.xml"); //$NON-NLS-1$
					deltaFile.renameTo(deltaOldFile);
					URI restoreLocation = URI.createFileURI(deltaOldFile.getAbsolutePath());

					File file = new File(restoreLocation.toFileString());

					if (file.exists()) {
						Document document = DocumentBuilderFactory.newInstance()
								.newDocumentBuilder().parse(file);
						IModelReconcilingService modelReconcilingService = new ModelReconcilingService();
						ModelReconciler modelReconciler = modelReconcilingService
								.createModelReconciler();
						document.normalizeDocument();
						Collection<ModelDelta> deltas = modelReconciler.constructDeltas(oldResource
								.getContents().get(0), document);
						modelReconcilingService.applyDeltas(deltas);
					}
				} catch (Exception e) {
					if (logger != null) {
						logger.error(e);
					}
				}
				if (appElement != null) {
					resource.getContents().add((EObject) appElement);
					if (!hasTopLevelWindows(resource) && logger != null) {
					}
				}
				return resource;
			}
		}

