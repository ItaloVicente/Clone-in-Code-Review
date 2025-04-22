@SuppressWarnings("restriction")
public class NestedProjectsLabelProvider extends ResourceExtensionLabelProvider {

	private IResourceChangeListener refreshSeveritiesOnProblemMarkerChange;
	private NestedProjectsProblemsModel model;
	private CompletableFuture<NestedProjectsProblemsModel> refreshModelJob;
	static final Set<StructuredViewer> viewersToUpdate = Collections.synchronizedSet(new LinkedHashSet<>());

	@Override
	public void init(ICommonContentExtensionSite aConfig) {
		super.init(aConfig);
		model = new NestedProjectsProblemsModel();
		refreshModelJob = refreshSeverities();
		refreshSeveritiesOnProblemMarkerChange = event -> {
			if (event.getDelta() == null) {
				return;
			}
			MarkerManager markerManager = ((Workspace) WorkbenchNavigatorPlugin.getWorkspace()).getMarkerManager();
			try {
				event.getDelta().accept(delta -> {
					IMarkerDelta[] markerDeltas = delta.getMarkerDeltas();
					for (IMarkerDelta markerDelta : markerDeltas) {
						if (markerManager.isSubtype(markerDelta.getType(), IMarker.PROBLEM)) {
							IResource resource = markerDelta.getResource();
							if (resource != null) {
								model.markDirty(resource);
							}
						}
					}
					return true;
				});
			} catch (CoreException e) {
				WorkbenchNavigatorPlugin.log(e.getMessage(),
						new Status(IStatus.ERROR, WorkbenchNavigatorPlugin.PLUGIN_ID, e.getMessage(), e));
			}
			if (model.isDirty()) {
				refreshModelJob = refreshSeverities();
				refreshModelJob.thenAccept(model -> {
					Object[] toUpdate = model.getResourcesWithModifiedSeverity().toArray();
					StructuredViewer[] viewers;
					synchronized (viewersToUpdate) {
						viewers = viewersToUpdate.toArray(new StructuredViewer[0]);
					}
					for (StructuredViewer viewer : viewers) {
						viewer.update(toUpdate, new String[] {});
					}
				});
			}
		};
		WorkbenchNavigatorPlugin.getWorkspace().addResourceChangeListener(refreshSeveritiesOnProblemMarkerChange);
	}

	@Override
	public void dispose() {
		WorkbenchNavigatorPlugin.getWorkspace().removeResourceChangeListener(refreshSeveritiesOnProblemMarkerChange);
		super.dispose();
	}
