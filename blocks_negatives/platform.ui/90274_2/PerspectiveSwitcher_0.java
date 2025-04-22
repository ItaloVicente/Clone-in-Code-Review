		menuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MPerspective persp = (MPerspective) menu.getData();
				if (persp != null)
					closePerspective(persp);
			}
		});
