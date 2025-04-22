				public Save[] promptToSave(Collection<MPart> dirtyParts) {
					LabelProvider labelProvider = new LabelProvider() {
						@Override
						public String getText(Object element) {
							return ((MPart) element).getLocalizedLabel();
						}
					};
					List<MPart> parts = new ArrayList<MPart>(dirtyParts);
					ListSelectionDialog dialog = new ListSelectionDialog(getShell(), parts,
							ArrayContentProvider.getInstance(), labelProvider,
							WorkbenchMessages.EditorManager_saveResourcesMessage);
					dialog.setInitialSelections(parts.toArray());
					dialog.setTitle(WorkbenchMessages.EditorManager_saveResourcesTitle);
					if (dialog.open() == IDialogConstants.CANCEL_ID) {
						return new Save[] { Save.CANCEL };
					}

					Object[] toSave = dialog.getResult();
					Save[] retSaves = new Save[parts.size()];
					Arrays.fill(retSaves, Save.NO);
					for (int i = 0; i < retSaves.length; i++) {
						MPart part = parts.get(i);
						for (Object o : toSave) {
							if (o == part) {
								retSaves[i] = Save.YES;
								break;
							}
						}
