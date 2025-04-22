				int newSelection = comboBox.getSelectionIndex();
				boolean changed = selection != newSelection;
				selection = newSelection;
				if (changed) {
					applyEditorValueAndDeactivate();
				}
