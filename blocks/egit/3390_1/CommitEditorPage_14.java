		body.addDisposeListener(new DisposeListener() {

			public void widgetDisposed(DisposeEvent e) {
				resources.dispose();
			}
		});
		FillLayout bodyLayout = new FillLayout();
		bodyLayout.marginHeight = 5;
		bodyLayout.marginWidth = 5;
		body.setLayout(bodyLayout);
