	private void createEditorItem(Table table, WorkbenchPage page, MPart part) {
		Object object = part.getObject();
		TableItem item = new TableItem(table, SWT.NONE);
		item.setText(WorkbenchMessages.CyclePartAction_editor);
		if (object instanceof CompatibilityEditor) {
			IEditorPart editor = ((CompatibilityEditor) object).getEditor();
			item.setImage(editor.getTitleImage());
			if (editor.getSite() instanceof PartSite) {
				item.setData(((PartSite) editor.getSite()).getPartReference());
				return;
			}
		} else {
			item.setImage(getImage(page, part));
		}
		item.setData(part);
	}

	private Image getImage(WorkbenchPage page, MPart part) {
		Object renderer = part.getRenderer();
		if (renderer instanceof SWTPartRenderer) {
			SWTPartRenderer partRenderer = (SWTPartRenderer) renderer;
			return partRenderer.getImage(part);
		}
		WorkbenchWindow wbw = (WorkbenchWindow) page.getWorkbenchWindow();
		if (wbw.getModel().getRenderer() instanceof SWTPartRenderer) {
			SWTPartRenderer partRenderer = (SWTPartRenderer) wbw.getModel().getRenderer();
			return partRenderer.getImage(part);
		}

		return null;
	}

