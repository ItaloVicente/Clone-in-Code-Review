	/**
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
