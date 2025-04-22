	private static final MarkerTransfer instance = new MarkerTransfer();

	private static final String TYPE_NAME = "marker-transfer-format" + System.currentTimeMillis() + ":" + instance.hashCode();//$NON-NLS-2$//$NON-NLS-1$

	private static final int TYPEID = registerType(TYPE_NAME);

	private IWorkspace workspace;

	private MarkerTransfer() {
	}

	private IMarker findMarker(String pathString, long id) {
		IPath path = new Path(pathString);
		IResource resource = workspace.getRoot().findMember(path);
		if (resource != null) {
			return resource.getMarker(id);
		}
		return null;
	}

	public static MarkerTransfer getInstance() {
		return instance;
	}

	@Override
