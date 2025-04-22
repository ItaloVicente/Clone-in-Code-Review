	 * A map of regular images to their decorated counterpart.
	 */
	private Map<Image, Image> decoratedImages = new HashMap<>();

	private ResourceManager resourceManager = new LocalResourceManager(
			JFaceResources.getResources());

	private Image annotatedTagImage = resourceManager
			.createImage(UIIcons.TAG_ANNOTATED);

	private Image gerritRepoImage = resourceManager
			.createImage(UIIcons.REPOSITORY_GERRIT);

	private final State verboseBranchModeState;

	private boolean verboseBranchMode = false;

	/**
	 * Constructs a repositories view label provider
	 */
	public RepositoriesViewLabelProvider() {
		ICommandService srv = CommonUtils.getService(PlatformUI.getWorkbench(), ICommandService.class);
		verboseBranchModeState = srv.getCommand(ToggleBranchCommitCommand.ID)
				.getState(ToggleBranchCommitCommand.TOGGLE_STATE);
		verboseBranchModeState.addListener(this);
		try {
			this.verboseBranchMode = ((Boolean) verboseBranchModeState
					.getValue()).booleanValue();
		} catch (Exception e) {
			Activator.logError(e.getMessage(), e);
		}
	}

	@Override
	public Image getImage(Object element) {
		RepositoryTreeNode node = (RepositoryTreeNode) element;
		RepositoryTreeNodeType type = node.getType();
		if (type == RepositoryTreeNodeType.TAG) {
			TagNode tagNode = (TagNode) node;
			if (tagNode.isAnnotated())
				return decorateImage(annotatedTagImage, element);
		} else if (type == RepositoryTreeNodeType.FILE) {
			Object object = node.getObject();
			if (object instanceof File) {
				ImageDescriptor descriptor = PlatformUI.getWorkbench()
						.getEditorRegistry()
						.getImageDescriptor(((File) object).getName());
				return decorateImage((Image) resourceManager.get(descriptor),
						element);
			}
		} else if (type == RepositoryTreeNodeType.REPO) {
			Object object = node.getObject();
			if (object instanceof Repository) {
				Repository r = (Repository) object;
				if (ResourcePropertyTester.hasGerritConfiguration(r))
					return gerritRepoImage;
			}
		}
		return decorateImage(node.getType().getIcon(), element);
	}

	@Override
	public String getText(Object element) {
		if (!(element instanceof RepositoryTreeNode))
			return null;

		RepositoryTreeNode node = (RepositoryTreeNode) element;

		return getSimpleText(node);
	}

	@Override
	public void dispose() {
		verboseBranchModeState.removeListener(this);
		for (Image image : decoratedImages.values()) {
			image.dispose();
		}
		resourceManager.dispose();
		decoratedImages.clear();
		super.dispose();
	}

	private Image decorateImage(final Image image, Object element) {

		RepositoryTreeNode node = (RepositoryTreeNode) element;
		switch (node.getType()) {

		case TAG:
		case ADDITIONALREF:
		case REF:
			String refName = ((Ref) node.getObject()).getName();
			Ref leaf = ((Ref) node.getObject()).getLeaf();

			String branchName;
			String compareString;

			try {
				branchName = node.getRepository().getFullBranch();
				if (branchName == null)
					return image;
				if (refName.startsWith(Constants.R_HEADS)) {
					compareString = refName;
				} else if (refName.startsWith(Constants.R_TAGS)) {
					TagNode tagNode = (TagNode) node;
					compareString = tagNode.getCommitId();
				} else if (refName.startsWith(Constants.R_REMOTES)) {
					ObjectId id = node.getRepository().resolve(refName);
					if (id == null)
						return image;
					try (RevWalk rw = new RevWalk(node.getRepository())) {
						RevCommit commit = rw.parseCommit(id);
						compareString = commit.getId().name();
					}
				} else if (refName.equals(Constants.HEAD)) {
					return getDecoratedImage(image);
				} else {
					String leafname = leaf.getName();
					if (leafname.startsWith(Constants.R_REFS)
							&& leafname.equals(node.getRepository()
									.getFullBranch())) {
						return getDecoratedImage(image);
					}
					ObjectId objectId = leaf.getObjectId();
					if (objectId != null && objectId.equals(
							node.getRepository().resolve(Constants.HEAD))) {
						return getDecoratedImage(image);
					}
					return image;
				}
			} catch (IOException e1) {
				return image;
			}

			if (compareString != null && compareString.equals(branchName)) {
				return getDecoratedImage(image);
			}

			return image;

		default:
			return image;
		}
	}

	private Image getDecoratedImage(final Image image) {
		Image decoratedImage = decoratedImages.get(image);
		if (decoratedImage == null) {
			CompositeImageDescriptor cd = new CompositeImageDescriptor() {

				@Override
				protected Point getSize() {
					Rectangle bounds = image.getBounds();
					return new Point(bounds.width, bounds.height);
				}

				@Override
				protected void drawCompositeImage(int width, int height) {
					drawImage(image.getImageData(), 0, 0);
					drawImage(UIIcons.OVR_CHECKEDOUT.getImageData(), 0, 0);

				}
			};
			decoratedImage = cd.createImage();
			decoratedImages.put(image, decoratedImage);
		}
		return decoratedImage;
	}

	private RevCommit getLatestCommit(RepositoryTreeNode node) {
		Ref ref = (Ref) node.getObject();
		ObjectId id;
		if (ref.isSymbolic())
			id = ref.getLeaf().getObjectId();
		else
			id = ref.getObjectId();
		if (id == null)
			return null;
		try (RevWalk walk = new RevWalk(node.getRepository())) {
			walk.setRetainBody(true);
			return walk.parseCommit(id);
		} catch (IOException ignored) {
			return null;
		}
	}

	private String abbreviate(final ObjectId id) {
		if (id != null)
			return id.abbreviate(7).name();
		else
			return ObjectId.zeroId().abbreviate(7).name();
	}

	/**
	 * Get styled text for submodule repository node
