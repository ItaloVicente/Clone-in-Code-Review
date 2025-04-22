				for (int i = 0; i < table.getColumnCount(); i++) {
					if (item.getBounds(i).contains(event.x, event.y)) {
						v.editElement(v.getStructuredSelection().getFirstElement(), i);
						editingSupport.setEnabled(false);
						break;
					}
