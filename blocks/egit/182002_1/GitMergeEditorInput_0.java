package org.eclipse.egit.ui.internal.merge;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareEditorInput;
import org.eclipse.compare.CompareViewerPane;
import org.eclipse.compare.IResourceProvider;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.contentmergeviewer.ContentMergeViewer;
import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.compare.structuremergeviewer.IDiffContainer;
import org.eclipse.compare.structuremergeviewer.IDiffElement;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.egit.core.internal.efs.HiddenResources;
import org.eclipse.egit.core.internal.indexdiff.IndexDiffCache;
import org.eclipse.egit.core.internal.indexdiff.IndexDiffCacheEntry;
import org.eclipse.egit.core.internal.util.ResourceUtil;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.commands.ActionHandler;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.team.internal.ui.synchronize.EditableSharedDocumentAdapter.ISharedDocumentAdapterListener;
import org.eclipse.team.internal.ui.synchronize.LocalResourceTypedElement;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.IHandlerActivation;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.ide.IDE.SharedImages;
import org.eclipse.ui.services.IServiceLocator;

@SuppressWarnings("restriction")
public abstract class AbstractGitMergeEditorInput extends CompareEditorInput {

	private static final Image FOLDER_IMAGE = PlatformUI.getWorkbench()
			.getSharedImages().getImage(ISharedImages.IMG_OBJ_FOLDER);

	private static final Image PROJECT_IMAGE = PlatformUI.getWorkbench()
			.getSharedImages().getImage(SharedImages.IMG_OBJ_PROJECT);

	private final IPath[] locations;

	private List<IFile> toDelete;

	private Map<String, IHandlerActivation> activations = new HashMap<>();

	private Repository repository;

	private Collection<String> gitPaths;

	private boolean initialized;

	protected AbstractGitMergeEditorInput(Repository repository,
			IPath... locations) {
		super(new CompareConfiguration());
		this.repository = repository;
		this.locations = locations;
	}

	@Override
	public Object getAdapter(Class adapter) {
		if ((adapter == IFile.class || adapter == IResource.class)
				&& isUIThread()) {
			Object selectedEdition = getSelectedEdition();
			if (selectedEdition instanceof DiffNode) {
				DiffNode diffNode = (DiffNode) selectedEdition;
				ITypedElement element = diffNode.getLeft();
				IResource resource = null;
				if (element instanceof HiddenResourceTypedElement) {
					resource = ((HiddenResourceTypedElement) element)
							.getRealFile();
				}
				if (resource == null && element instanceof IResourceProvider) {
					resource = ((IResourceProvider) element).getResource();
				}
				if (resource != null && adapter.isInstance(resource)) {
					return resource;
				}
			}
		}
		return super.getAdapter(adapter);
	}

	@Override
	protected void contentsCreated() {
		super.contentsCreated();
		getNavigator().selectChange(true);
	}

	@Override
	public Viewer findContentViewer(Viewer oldViewer, ICompareInput input,
			Composite parent) {
		Viewer newViewer = super.findContentViewer(oldViewer, input, parent);
		ToolBarManager manager = CompareViewerPane.getToolBarManager(parent);
		if (manager != null) {
			initActions(manager, newViewer, input);
		}
		return newViewer;
	}

	@FunctionalInterface
	protected interface ActionSupplier {

		public CompareEditorInputViewerAction get(boolean create);
	}

	protected void initActions(ToolBarManager manager, Viewer newViewer,
			ICompareInput input) {
	}

	protected void setAction(ToolBarManager manager, Viewer viewer,
			boolean isApplicable, String id, ActionSupplier supplier) {
		IContributionItem item = manager.find(id);
		if (item != null) {
			if (item instanceof ActionContributionItem) {
				IAction action = ((ActionContributionItem) item).getAction();
				if (action instanceof CompareEditorInputViewerAction) {
					((CompareEditorInputViewerAction) action).setViewer(
							isApplicable ? (ContentMergeViewer) viewer : null);
					action.setEnabled(isApplicable);
					if (item.isVisible() != isApplicable) {
						item.setVisible(isApplicable);
						manager.update(true);
					}
				}
			}
		} else if (isApplicable) {
			CompareEditorInputViewerAction action = supplier.get(true);
			action.setViewer((ContentMergeViewer) viewer);
			action.setEnabled(true);
			manager.insert(0, new ActionContributionItem(action));
			manager.update(true);
			registerAction(action, id);
		} else {
			CompareEditorInputViewerAction action = supplier.get(false);
			if (action != null) {
				action.setEnabled(false);
			}
		}
	}

	private void registerAction(IAction action, String commandId) {
		if (activations.containsKey(commandId)) {
			return;
		}
		action.setActionDefinitionId(commandId);
		IServiceLocator locator = getContainer().getServiceLocator();
		if (locator != null) {
			IHandlerService handlers = locator
					.getService(IHandlerService.class);
			if (handlers != null) {
				activations.put(commandId, handlers.activateHandler(commandId,
						new ActionHandler(action)));
			}
		}
	}

	protected void disposeActions() {
	}

	@Override
	protected void handleDispose() {
		super.handleDispose();
		activations.values()
				.forEach(a -> a.getHandlerService().deactivateHandler(a));
		activations.clear();
		disposeActions();
		PlatformUI.getWorkbench().getDisplay().asyncExec(this::cleanUp);
	}

	private void cleanUp() {
		if (toDelete == null || toDelete.isEmpty()) {
			return;
		}
		List<IFile> toClean = toDelete;
		toDelete = null;
		Job job = new Job(UIText.GitMergeEditorInput_ResourceCleanupJobName) {

			@Override
			public boolean shouldSchedule() {
				return super.shouldSchedule()
						&& !PlatformUI.getWorkbench().isClosing();
			}

			@Override
			public boolean shouldRun() {
				return super.shouldRun()
						&& !PlatformUI.getWorkbench().isClosing();
			}

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				IWorkspaceRunnable remove = m -> {
					SubMonitor progress = SubMonitor.convert(m, toClean.size());
					for (IFile tmp : toClean) {
						if (PlatformUI.getWorkbench().isClosing()) {
							return;
						}
						try {
							tmp.delete(true, progress.newChild(1));
						} catch (CoreException e) {
						}
					}
				};
				try {
					ResourcesPlugin.getWorkspace().run(remove, null,
							IWorkspace.AVOID_UPDATE, monitor);
				} catch (CoreException e) {
					return e.getStatus();
				}
				return Status.OK_STATUS;
			}
		};
		job.setSystem(true);
		job.setUser(false);
		job.schedule();
	}

	private static boolean isUIThread() {
		return Display.getCurrent() != null;
	}


	protected LocalResourceTypedElement createWithHiddenResource(URI uri,
			String name, IFile file, Charset encoding) throws IOException {
		IFile tmp = createHiddenResource(uri, name, encoding);
		return new HiddenResourceTypedElement(tmp, file);
	}

	protected IFile createHiddenResource(URI uri, String name, Charset encoding)
			throws IOException {
		try {
			IFile tmp = HiddenResources.INSTANCE.createFile(uri, name, encoding,
					null);
			if (toDelete == null) {
				toDelete = new ArrayList<>();
			}
			toDelete.add(tmp);
			return tmp;
		} catch (CoreException e) {
			throw new IOException(e.getMessage(), e);
		}
	}

	protected IDiffContainer getFileParent(IDiffContainer root,
			IPath repositoryPath, IFile file, IPath location) {
		int projectSegment = -1;
		String projectName = null;
		if (file != null) {
			IProject project = file.getProject();
			IPath projectLocation = project.getLocation();
			if (projectLocation != null) {
				IPath projectPath = project.getLocation().makeRelativeTo(
						repositoryPath);
				projectSegment = projectPath.segmentCount() - 1;
				projectName = project.getName();
			}
		}

		IPath path = location.makeRelativeTo(repositoryPath);
		IDiffContainer child = root;
		for (int i = 0; i < path.segmentCount() - 1; i++) {
			if (i == projectSegment)
				child = getOrCreateChild(child, projectName, true);
			else
				child = getOrCreateChild(child, path.segment(i), false);
		}
		return child;
	}

	private DiffNode getOrCreateChild(IDiffContainer parent, final String name,
			final boolean projectMode) {
		for (IDiffElement child : parent.getChildren()) {
			if (child.getName().equals(name)) {
				return ((DiffNode) child);
			}
		}
		DiffNode child = new DiffNode(parent, Differencer.NO_CHANGE) {

			@Override
			public String getName() {
				return name;
			}

			@Override
			public Image getImage() {
				if (projectMode)
					return PROJECT_IMAGE;
				else
					return FOLDER_IMAGE;
			}
		};
		return child;
	}

	@Override
	public boolean canRunAsJob() {
		return true;
	}

	@Override
	protected final Object prepareInput(IProgressMonitor monitor)
			throws InvocationTargetException, InterruptedException {
		monitor.beginTask(UIText.GitMergeEditorInput_CheckingResourcesTaskName,
				IProgressMonitor.UNKNOWN);
		try {
			initPaths();
			if (monitor.isCanceled()) {
				throw new InterruptedException();
			}
			return buildInput(monitor);
		} finally {
			monitor.done();
		}
	}

	protected abstract Object buildInput(IProgressMonitor monitor)
			throws InvocationTargetException, InterruptedException;

	private void initPaths() throws InvocationTargetException {
		if (initialized) {
			return;
		}
		initialized = true;
		if (repository == null || locations != null && locations.length > 0) {
			Map<Repository, Collection<String>> pathsByRepository = ResourceUtil
					.splitPathsByRepository(Arrays.asList(locations));
			if (pathsByRepository.size() != 1) {
				throw new InvocationTargetException(new IllegalStateException(
						UIText.RepositoryAction_multiRepoSelection));
			}
			Entry<Repository, Collection<String>> entry = pathsByRepository
					.entrySet().iterator().next();
			Repository repo = entry.getKey();
			if (repository != null
					&& !repo.getDirectory().equals(repository.getDirectory())) {
				throw new InvocationTargetException(
						new IllegalStateException("Paths not in repo " //$NON-NLS-1$
								+ repository.getDirectory()));
			}
			if (repository == null) {
				repository = repo;
			}
			gitPaths = new ArrayList<>(entry.getValue());
		}
	}

	protected Repository getRepository() {
		return repository;
	}

	protected Collection<String> getFilterPaths() {
		if (gitPaths == null) {
			return Collections.emptyList();
		}
		return gitPaths;
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(locations);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		AbstractGitMergeEditorInput other = (AbstractGitMergeEditorInput) obj;
		return Arrays.equals(locations, other.locations);
	}

	protected static class LocalResourceSaver
			implements ISharedDocumentAdapterListener {

		LocalResourceTypedElement element;

		public LocalResourceSaver(LocalResourceTypedElement element) {
			this.element = element;
		}

		protected void save() throws CoreException {
			element.saveDocument(true, null);
			refreshIndexDiff();
		}

		private void refreshIndexDiff() {
			IResource resource = element.getResource();
			if (resource != null && HiddenResources.INSTANCE
					.isHiddenProject(resource.getProject())) {
				String gitPath = null;
				Repository repository = null;
				URI uri = resource.getLocationURI();
				if (EFS.SCHEME_FILE.equals(uri.getScheme())) {
					IPath location = new Path(uri.getSchemeSpecificPart());
					repository = ResourceUtil.getRepository(location);
					if (repository != null) {
						location = ResourceUtil.getRepositoryRelativePath(
								location, repository);
						if (location != null) {
							gitPath = location.toPortableString();
						}
					}
				} else {
					repository = HiddenResources.INSTANCE.getRepository(uri);
					if (repository != null) {
						gitPath = HiddenResources.INSTANCE.getGitPath(uri);
					}
				}
				if (gitPath != null && repository != null) {
					IndexDiffCacheEntry indexDiffCacheForRepository = IndexDiffCache
							.getInstance().getIndexDiffCacheEntry(repository);
					if (indexDiffCacheForRepository != null) {
						indexDiffCacheForRepository.refreshFiles(
								Collections.singletonList(gitPath));
					}
				}
			}
		}

		@Override
		public void handleDocumentConnected() {
		}

		@Override
		public void handleDocumentDisconnected() {
		}

		@Override
		public void handleDocumentFlushed() {
			try {
				save();
			} catch (CoreException e) {
				Activator.handleStatus(e.getStatus(), true);
			}
		}

		@Override
		public void handleDocumentDeleted() {
		}

		@Override
		public void handleDocumentSaved() {
		}
	}

	protected static class HiddenResourceTypedElement
			extends LocalResourceTypedElement {

		private final IFile realFile;

		private HiddenResourceTypedElement(IFile file, IFile realFile) {
			super(file);
			this.realFile = realFile;
		}

		public IFile getRealFile() {
			return realFile;
		}

		@Override
		public boolean equals(Object obj) {
			return super.equals(obj);
		}

		@Override
		public int hashCode() {
			return super.hashCode();
		}
	}
}
