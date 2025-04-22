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
	 *
	 * @param node
	 * @return styled string
	 */
	protected StyledString getStyledTextForSubmodule(RepositoryTreeNode node) {
		Repository repository = (Repository) node.getObject();
		if (repository == null) {
			return new StyledString();
		}
		StyledString string = GitLabels.getChangedPrefix(repository);

		String path = Repository.stripWorkDir(node.getParent().getRepository()
				.getWorkTree(), repository.getWorkTree());
		string.append(path);

		Ref head;
		try {
			head = repository.exactRef(Constants.HEAD);
		} catch (IOException e) {
			return string;
		}
		if (head != null) {
			string.append(' ');
			string.append('[', StyledString.DECORATIONS_STYLER);
			if (head.isSymbolic())
				string.append(
						Repository.shortenRefName(head.getLeaf().getName()),
						StyledString.DECORATIONS_STYLER);
			else if (head.getObjectId() != null)
				string.append(abbreviate(head.getObjectId()),
						StyledString.DECORATIONS_STYLER);
			string.append(']', StyledString.DECORATIONS_STYLER);
			if (verboseBranchMode && head.getObjectId() != null) {
				RevCommit commit;
				try (RevWalk walk = new RevWalk(repository)) {
					commit = walk.parseCommit(head.getObjectId());
					string.append(' ');
					string.append(commit.getShortMessage(),
							StyledString.QUALIFIER_STYLER);
				} catch (IOException ignored) {
				}
			}
		}
		return string;
	}

	/**
	 * Get styled text for commit node
	 *
	 * @param node
	 * @return styled string
	 */
	protected StyledString getStyledTextForCommit(StashedCommitNode node) {
		StyledString string = new StyledString();
		RevCommit commit = node.getObject();
		string.append(MessageFormat.format("{0}@'{'{1}'}'", //$NON-NLS-1$
				Constants.STASH, Integer.valueOf(node.getIndex())));
		string.append(' ');
		string.append('[', StyledString.DECORATIONS_STYLER);
		string.append(abbreviate(commit), StyledString.DECORATIONS_STYLER);
		string.append(']', StyledString.DECORATIONS_STYLER);
		string.append(' ');
		string.append(commit.getShortMessage(), StyledString.QUALIFIER_STYLER);
		return string;
	}

	/**
	 * Gets the {@link StyledString} for a {@link SubmodulesNode}.
	 *
	 * @param node
	 *            to get the text for
	 * @return the {@link StyledString}
	 */
	protected StyledString getStyledTextForSubmodules(SubmodulesNode node) {
		String label = getSimpleText(node);
		if (label == null) {
			return new StyledString();
		}
		StyledString styled = new StyledString(label);
		Repository repository = node.getRepository();
		if (repository != null) {
			boolean hasChanges = false;
			try (SubmoduleWalk walk = SubmoduleWalk.forIndex(repository)) {
				while (!hasChanges && walk.next()) {
					Repository submodule = walk.getRepository();
					if (submodule != null) {
						Repository cached = org.eclipse.egit.core.Activator
								.getDefault().getRepositoryCache()
								.lookupRepository(submodule.getDirectory()
										.getAbsoluteFile());
						hasChanges = cached != null
								&& RepositoryUtil.hasChanges(cached);
						submodule.close();
					}
				}
			} catch (IOException e) {
				hasChanges = false;
			}
			if (hasChanges) {
				StyledString prefixed = new StyledString();
				prefixed.append('>', StyledString.DECORATIONS_STYLER);
				prefixed.append(' ').append(styled);
				return prefixed;
			}
		}
		return styled;
