
	@Override
	protected void initListeners(TreeViewer viewer) {
		super.initListeners(viewer);

		viewer.getControl().addMouseListener(new MouseAdapter() {

			@Override
			public void mouseUp(MouseEvent e) {
				if (e.button == 2 && e.widget instanceof Tree) {
					TreeItem item = ((Tree) e.widget).getItem(new Point(e.x, e.y));
					if (item == null) {
						return;
					}

					Object data = item.getData();
					if (data instanceof IProject) {
						IProject project = (IProject) data;

						try {
							if (project.isOpen()) {
								project.close(new NullProgressMonitor());
							}
						} catch (CoreException ex) {
						}
					}
				}
			}
		});
	}
