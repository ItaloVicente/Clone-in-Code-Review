package org.eclipse.jface.viewers;

public class DefaultColumnViewerEditingSupport extends EditingSupport {

	private int columnIndex;

	public DefaultColumnViewerEditingSupport(ColumnViewer viewer, int columnIndex) {
		super(viewer);
		this.columnIndex = columnIndex;
	}

	@Override
	public boolean canEdit(Object element) {
		Object[] properties = getViewer().getColumnProperties();

		if (columnIndex < properties.length) {
			return getViewer().getCellModifier().canModify(element,
					(String) getViewer().getColumnProperties()[columnIndex]);
		}

		return false;
	}

	@Override
	public CellEditor getCellEditor(Object element) {
		CellEditor[] editors = getViewer().getCellEditors();
		if (columnIndex < editors.length) {
			return getViewer().getCellEditors()[columnIndex];
		}
		return null;
	}

	@Override
	public Object getValue(Object element) {
		Object[] properties = getViewer().getColumnProperties();

		if (columnIndex < properties.length) {
			return getViewer().getCellModifier().getValue(element,
					(String) getViewer().getColumnProperties()[columnIndex]);
		}

		return null;
	}

	@Override
	public void setValue(Object element, Object value) {
		Object[] properties = getViewer().getColumnProperties();

		if (columnIndex < properties.length) {
			getViewer().getCellModifier().modify(getViewer().findItem(element),
					(String) getViewer().getColumnProperties()[columnIndex],
					value);
		}
	}

	@Override
	boolean isLegacySupport() {
		return true;
	}

}
