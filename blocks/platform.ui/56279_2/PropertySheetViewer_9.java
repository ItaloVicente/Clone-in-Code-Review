		}
		return null;
	}

	private void fireCellEditorActivated(CellEditor activatedCellEditor) {
		Object[] listeners = activationListeners.getListeners();
		for (int i = 0; i < listeners.length; ++i) {
			((ICellEditorActivationListener) listeners[i])
					.cellEditorActivated(activatedCellEditor);
		}
	}

	private void fireCellEditorDeactivated(CellEditor activatedCellEditor) {
		Object[] listeners = activationListeners.getListeners();
		for (int i = 0; i < listeners.length; ++i) {
			((ICellEditorActivationListener) listeners[i])
					.cellEditorDeactivated(activatedCellEditor);
		}
	}

	public CellEditor getActiveCellEditor() {
		return cellEditor;
	}

	private TreeItem[] getChildItems(Widget widget) {
		if (widget instanceof Tree) {
			return ((Tree) widget).getItems();
		}
		else if (widget instanceof TreeItem) {
			return ((TreeItem) widget).getItems();
		}
		return new TreeItem[0];
	}

	private List getChildren(Object node) {
		IPropertySheetEntry entry = null;
		PropertySheetCategory category = null;
		if (node instanceof IPropertySheetEntry) {
