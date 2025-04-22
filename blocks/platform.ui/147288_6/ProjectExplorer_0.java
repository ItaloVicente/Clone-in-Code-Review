
	@Override
	protected void initListeners(TreeViewer viewer) {
		super.initListeners(viewer);

		viewer.getControl().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent event) {
				SafeRunner.run(() -> {
					handleMiddleClick(event);
				});
			}

			private void handleMiddleClick(MouseEvent event) {
				if (event.button == 2 && event.widget instanceof Tree) {
					TreeItem item = ((Tree) event.widget).getItem(new Point(event.x, event.y));
					if (item == null) {
						return;
					}
					Object data = item.getData();
					if (data instanceof IProject) {
						IProject project = (IProject) data;
						CloseResourceAction cra = new CloseResourceAction(() -> null);
						cra.selectionChanged(new StructuredSelection(project));
						cra.run();
					}
				}
			}
		});
	}
