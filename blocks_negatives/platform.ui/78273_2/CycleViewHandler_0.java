					IEditorPart activeEditor = page.getActiveEditor();
					TableItem item = new TableItem(table, SWT.NONE);
					item.setText(WorkbenchMessages.CyclePartAction_editor);
					item.setImage(activeEditor.getTitleImage());
					if (activeEditor.getSite() instanceof PartSite) {
						item.setData(((PartSite) activeEditor.getSite()).getPartReference());
					} else {
						item.setData(part);
					}
