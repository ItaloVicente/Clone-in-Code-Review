				private boolean saveParts(ArrayList<MPart> dirtyParts, Save[] decisions) {
					if (decisions == null || decisions.length == 0) {
						super.saveParts(dirtyParts, true);
					}
					if (dirtyParts.size() != decisions.length) {
						for (Save decision : decisions) {
							if (decision == Save.CANCEL) {
								return false;
							}
						}
					}
					List<MPart> dirtyPartsList = Collections.unmodifiableList(new ArrayList<>(dirtyParts));
					for (Save decision : decisions) {
						if (decision == Save.CANCEL) {
							return false;
						}
					}

					for (int i = 0; i < decisions.length; i++) {
						if (decisions[i] == Save.YES) {
							if (!save(dirtyPartsList.get(i), false)) {
								return false;
							}
						}
					}
					return true;
				}

				private boolean saveMixedParts(ArrayList<MPart> nonCompParts, ArrayList<IWorkbenchPart> compParts,
						boolean confirm) {
					SaveablesList saveablesList = (SaveablesList) PlatformUI.getWorkbench()
							.getService(ISaveablesLifecycleListener.class);
					if (!confirm) {
						boolean saved = super.saveParts(nonCompParts, confirm);
						Object saveResult = saveablesList.preCloseParts(compParts, true, WorkbenchWindow.this);
						return ((saveResult != null) && saved);
					}
					LabelProvider labelProvider = new LabelProvider() {
						WorkbenchPartLabelProvider workbenchLabelProvider = new WorkbenchPartLabelProvider();
						@Override
						public String getText(Object element) {
							if (element instanceof Saveable) {
								return workbenchLabelProvider.getText(element);
							}
							return ((MPart) element).getLocalizedLabel();
						}
					};
					ArrayList<Object> listParts = new ArrayList<>();
					Map<IWorkbenchPart, List<Saveable>> saveableMap = saveablesList.getSaveables(compParts);
					listParts.addAll(nonCompParts);
					LinkedHashSet<Saveable> saveablesSet = new LinkedHashSet<>();
					for (IWorkbenchPart workbenchPart : compParts) {
						List<Saveable> list = saveableMap.get(workbenchPart);
						if (list != null) {
							saveablesSet.addAll(list);
						}
					}
					listParts.addAll(saveablesSet);
					ListSelectionDialog dialog = new ListSelectionDialog(getShell(), listParts,
							ArrayContentProvider.getInstance(), labelProvider,
							WorkbenchMessages.EditorManager_saveResourcesMessage);
					dialog.setInitialSelections(listParts.toArray());
					dialog.setTitle(WorkbenchMessages.EditorManager_saveResourcesTitle);
					if (dialog.open() == IDialogConstants.CANCEL_ID) {
						return false;
					}

					Object[] toSave = dialog.getResult();
					Save[] nonCompatSaves = new Save[nonCompParts.size()];
					Save[] compatSaves = new Save[compParts.size()];
					Arrays.fill(nonCompatSaves, Save.NO);
					Arrays.fill(compatSaves, Save.NO);
					for (int i = 0; i < nonCompatSaves.length; i++) {
						MPart part = nonCompParts.get(i);
						for (Object o : toSave) {
							if (o == part) {
								nonCompatSaves[i] = Save.YES;
								break;
							}
						}
					}
					Map<Saveable, Save> saveOptionMap = new HashMap<>();
					for (Saveable saveable: saveablesSet) {
						boolean found = false;
						for (Object o : toSave) {
							if (o == saveable) {
								saveOptionMap.put(saveable, Save.YES);
								found = true;
								break;
							}
						}
						if (!found) {
							saveOptionMap.put(saveable, Save.NO);
						}
					}
					boolean saved = saveParts(nonCompParts, nonCompatSaves);
					if (!saved) {
						return saved;
					}
					Object saveResult = saveablesList.preCloseParts(compParts, true, WorkbenchWindow.this,
							saveOptionMap);
					return ((saveResult != null) && saved);
				}

