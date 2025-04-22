	@Override
	public Object getSelectedEdition() {
		if (isUIThread()) {
			return super.getSelectedEdition();
		} else {
			Object[] item = { null };
			PlatformUI.getWorkbench().getDisplay().syncExec(() -> {
				item[0] = super.getSelectedEdition();
			});
			return item[0];
		}
	}

	private boolean isMultiFile() {
		Object input = getCompareResult();
		if (input instanceof IDiffContainer) {
			IDiffElement[] children = ((IDiffContainer) input).getChildren();
			if (children.length != 1) {
				return true;
			}
			if (children[0] instanceof IDiffContainer
					&& ((IDiffContainer) children[0]).hasChildren()) {
				return true;
			}
		}
		return false;
	}

	private ITypedElement getElement() {
		Object selectedEdition = getSelectedEdition();
		if (selectedEdition instanceof DiffNode) {
			DiffNode diffNode = (DiffNode) selectedEdition;
			return diffNode.getLeft();
		}
		return null;
	}

	private DiffNode getNode() {
		Object selectedEdition = getSelectedEdition();
		if (selectedEdition instanceof DiffNode) {
			return (DiffNode) selectedEdition;
		}
		return null;
	}

	private IResource getResource(ITypedElement element) {
		if (element != null) {
			IResource resource = null;
			if (element instanceof HiddenResourceTypedElement) {
				resource = ((HiddenResourceTypedElement) element).getRealFile();
			} else if (element instanceof IResourceProvider) {
				resource = ((IResourceProvider) element).getResource();
			}
			return resource;
		}
		return null;
	}

	protected GitInfo getGitInfo(IPath path) {
		return null;
	}

	private IShowInSource getShowInSource(ShowInContext context) {
		return () -> context;
	}

