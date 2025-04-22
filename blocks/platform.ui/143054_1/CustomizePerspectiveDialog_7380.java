			@Override
			public void widgetSelected(SelectionEvent e) {
				if (showCommandGroupFilterButton.getSelection()) {
					Object o = ((StructuredSelection) menuStructureViewer1.getSelection()).getFirstElement();
					ActionSet initSelectAS = null;
					DisplayItem initSelectCI = null;
					if (o instanceof DisplayItem) {
						initSelectCI = ((DisplayItem) o);
						initSelectAS = initSelectCI.getActionSet();
