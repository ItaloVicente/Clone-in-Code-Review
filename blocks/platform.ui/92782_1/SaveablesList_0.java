					Map<String, Integer> buttonLabelToIdMap = new LinkedHashMap<>();
					buttonLabelToIdMap.put(WorkbenchMessages.Save, IDialogConstants.OK_ID);
					buttonLabelToIdMap.put(WorkbenchMessages.Dont_Save, IDialogConstants.NO_ID);
					if (canCancel) {
						buttonLabelToIdMap.put(IDialogConstants.CANCEL_LABEL, IDialogConstants.CANCEL_ID);
					}
