		ctxSrv = CommonUtils.getService(getSite(), IContextService.class);
		viewer.addSelectionChangedListener(event -> {
			handleRenameContext(event.getSelection(), viewer);
		});
		viewer.getTree().addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				handleRenameContext(viewer.getSelection(), viewer);
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (renameContext != null) {
					ctxSrv.deactivateContext(renameContext);
					renameContext = null;
				}
			}

		});
