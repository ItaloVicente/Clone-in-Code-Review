	}

	private static final class LabelProvider extends BaseLabelProvider
			implements ITableLabelProvider {


		/**
		 *
		 * @param viewer
		 */
		LabelProvider(final TreeViewer viewer) {

			viewer.setLabelProvider(this);
			Tree tree = viewer.getTree();
			TreeColumn col = new TreeColumn(tree, SWT.NONE);
			col.setWidth(400);
			viewer.getTree().addMouseTrackListener(new MouseTrackAdapter() {

				@Override
				public void mouseHover(MouseEvent e) {


				}

			});

		}

		public Image getColumnImage(Object element, int columnIndex) {
			return decorateImage(((RepositoryTreeNode) element).getType()
					.getIcon(), element);
		}

		public String getColumnText(Object element, int columnIndex) {

			RepositoryTreeNode node = (RepositoryTreeNode) element;
			switch (node.getType()) {
			case REPO:
				File directory = ((Repository) node.getObject()).getDirectory()
						.getParentFile();
						.getAbsolutePath());
			case BRANCHES:
				return RepositoryViewUITexts.RepositoriesView_Branches_Nodetext;
			case REMOTES:
				return RepositoryViewUITexts.RepositoriesView_RemotesNodeText;
			case REMOTE:
				String name = (String) node.getObject();
				String url = node.getRepository().getConfig().getString(REMOTE,
						name, URL);
				return name;
			case PROJECTS:
				return RepositoryViewUITexts.RepositoriesView_ExistingProjects_Nodetext;
			case REF:
				Ref ref = (Ref) node.getObject();
				String refName = node.getRepository().shortenRefName(
						ref.getName());
				if (ref.isSymbolic()) {
					refName = refName
							+ node.getRepository().shortenRefName(
									ref.getLeaf().getName());
				}
				return refName;
			case PROJ:

				File file = (File) node.getObject();
				return file.getName();

			default:
				return null;
			}
		}

		public Image decorateImage(final Image image, Object element) {

			RepositoryTreeNode node = (RepositoryTreeNode) element;
			switch (node.getType()) {

			case REF:
				Ref ref = (Ref) node.getObject();
				String refName = node.getRepository().shortenRefName(
						ref.getName());
				try {
					String branch = node.getBranch();
					if (refName.equals(branch)) {
						CompositeImageDescriptor cd = new CompositeImageDescriptor() {

							@Override
							protected Point getSize() {
								return new Point(image.getBounds().width, image
										.getBounds().width);
							}

							@Override
							protected void drawCompositeImage(int width,
									int height) {
								drawImage(image.getImageData(), 0, 0);
								drawImage(
										UIIcons.OVR_CHECKEDOUT.getImageData(),
										0, 0);

							}
						};
						return cd.createImage();
					}
				} catch (IOException e1) {
				}
				return image;

			case PROJ:

				File file = (File) node.getObject();

				for (IProject proj : ResourcesPlugin.getWorkspace().getRoot()
						.getProjects()) {
					if (proj.getLocation().equals(
							new Path(file.getAbsolutePath()))) {
						CompositeImageDescriptor cd = new CompositeImageDescriptor() {

							@Override
							protected Point getSize() {
								return new Point(image.getBounds().width, image
										.getBounds().width);
							}

							@Override
							protected void drawCompositeImage(int width,
									int height) {
								drawImage(image.getImageData(), 0, 0);
								drawImage(
										UIIcons.OVR_CHECKEDOUT.getImageData(),
										0, 0);

							}
						};
						return cd.createImage();
					}
				}
				return image;

			default:
				return image;
			}
		}

	}

	private List<String> getGitDirs() {
