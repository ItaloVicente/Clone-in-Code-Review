		ctxSrv = CommonUtils.getService(getSite(), IContextService.class);
		viewer.addSelectionChangedListener(event -> {
			handleSingleRepositoryContext(event.getSelection(), viewer);
		});
		viewer.getTree().addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				handleSingleRepositoryContext(null, viewer);
			}

			@Override
			public void focusGained(FocusEvent e) {
				handleSingleRepositoryContext(viewer.getSelection(), viewer);
			}
		});
