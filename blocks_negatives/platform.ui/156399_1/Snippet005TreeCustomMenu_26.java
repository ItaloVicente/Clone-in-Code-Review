		mgr.addMenuListener(new IMenuListener() {

			@Override
			public void menuAboutToShow(IMenuManager manager) {
				IStructuredSelection selection = v.getStructuredSelection();
				if (!selection.isEmpty()) {
					a.setText("Action for " + selection.getFirstElement());
					mgr.add(a);
				}
