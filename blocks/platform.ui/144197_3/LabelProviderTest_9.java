			@Override
			public void updateLabel(ViewerLabel label, Object element) {
				if (element instanceof RenamableItem) {
					RenamableItem item = (RenamableItem) element;

					label.setText(item.getName());
