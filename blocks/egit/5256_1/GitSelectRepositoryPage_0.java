		tv.addDoubleClickListener(new IDoubleClickListener() {

			public void doubleClick(DoubleClickEvent event) {
				checkPage();
				if (isPageComplete())
					getContainer().showPage(getNextPage());
			}
		});

