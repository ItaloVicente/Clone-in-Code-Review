
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				MPerspective persp = (MPerspective) e.widget.getData();
				persp.getParent().setSelectedElement(persp);
			}
