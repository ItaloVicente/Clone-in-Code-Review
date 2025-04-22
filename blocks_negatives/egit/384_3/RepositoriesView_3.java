					Point eventPoint = new Point(e.x, e.y);

					TreeItem item = viewer.getTree().getItem(eventPoint);
					if (item != null) {

						RepositoryTreeNode node = (RepositoryTreeNode) item
								.getData();
						String text = node.getRepository().getDirectory()
								.getAbsolutePath();

						final ViewerCell cell = viewer.getCell(eventPoint);

						if (infoControl != null && infoControl.isVisible()) {
							infoControl.setVisible(false);
						}

						GC testGc = new GC(cell.getControl());
						final Point textExtent = testGc.textExtent(text);
						testGc.dispose();

						if (infoControl == null || !infoControl.isVisible()) {

							IInformationPresenter ips = new IInformationPresenter() {

								public String updatePresentation(
										Display display, String hoverInfo,
										TextPresentation presentation,
										int maxWidth, int maxHeight) {
									return hoverInfo;
								}

							};

							infoControl = new DefaultInformationControl(Display
									.getCurrent().getActiveShell().getShell(),
									ips) {

								@Override
								public void setInformation(String content) {
									super.setInformation(content);
									super.setSize(textExtent.x, textExtent.y);
								}

							};
						}

						Point dispPoint = viewer.getControl().toDisplay(
								eventPoint);

						infoControl.setLocation(dispPoint);

						infoControl.setInformation(text);

						final MouseMoveListener moveListener = new MouseMoveListener() {

							public void mouseMove(MouseEvent evt) {
								infoControl.setVisible(false);
								cell.getControl().removeMouseMoveListener(this);

							}
						};

						cell.getControl().addMouseMoveListener(moveListener);

						infoControl.setVisible(true);

					}
