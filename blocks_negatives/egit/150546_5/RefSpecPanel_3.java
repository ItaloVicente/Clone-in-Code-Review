				return UIText.RefSpecPanel_forceFalseDescription + '\n'
						+ UIText.RefSpecPanel_clickToChange;
			}
		});
		column.setEditingSupport(new EditingSupport(tableViewer) {
			@Override
			protected boolean canEdit(final Object element) {
				return !isDeleteRefSpec(element);
			}

			@Override
			protected CellEditor getCellEditor(final Object element) {
				return forceUpdateCellEditor;
			}

			@SuppressWarnings("boxing")
			@Override
			protected Object getValue(final Object element) {
				return ((RefSpec) element).isForceUpdate();
