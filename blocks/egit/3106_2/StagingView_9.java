package org.eclipse.egit.ui.internal.staging;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.compare.IContentChangeListener;
import org.eclipse.compare.IContentChangeNotifier;
import org.eclipse.compare.ITypedElement;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.MultiRule;
import org.eclipse.egit.core.IteratorService;
import org.eclipse.egit.core.internal.storage.GitFileRevision;
import org.eclipse.egit.core.op.AddToIndexOperation;
import org.eclipse.egit.core.project.RepositoryMapping;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIText;
import org.eclipse.egit.ui.internal.CompareUtils;
import org.eclipse.egit.ui.internal.EditableRevision;
import org.eclipse.egit.ui.internal.GitCompareFileRevisionEditorInput;
import org.eclipse.egit.ui.internal.LocalFileRevision;
import org.eclipse.egit.ui.internal.trace.GitTraceLocation;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.DecoratingStyledCellLabelProvider;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheEditor;
import org.eclipse.jgit.dircache.DirCacheEditor.PathEdit;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.events.IndexChangedListener;
import org.eclipse.jgit.events.ListenerHandle;
import org.eclipse.jgit.events.RefsChangedListener;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.IndexDiff;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.team.core.history.IFileRevision;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.progress.IWorkbenchSiteProgressService;
import org.eclipse.ui.progress.WorkbenchJob;

public class StagingView extends ViewPart implements IResourceChangeListener {

	private Set<Repository> displayedRepositories = new HashSet<Repository>();

	private Map<Repository, Set<IFile>> staged = new HashMap<Repository, Set<IFile>>();

	private Map<Repository, Set<IFile>> modified = new HashMap<Repository, Set<IFile>>();

	private Map<Repository, Set<IFile>> untracked = new HashMap<Repository, Set<IFile>>();

	private Map<Repository, Set<IFile>> conflicts = new HashMap<Repository, Set<IFile>>();

	private final RefsChangedListener myRefsChangedListener = new RefsChangedListener() {
		public void onRefsChanged(org.eclipse.jgit.events.RefsChangedEvent event) {
			reload(event.getRepository());
		}
	};

	private final IndexChangedListener myIndexChangedListener = new IndexChangedListener() {
		public void onIndexChanged(
				org.eclipse.jgit.events.IndexChangedEvent event) {
			reload(event.getRepository());
		}
	};

	private final List<ListenerHandle> myListeners = new LinkedList<ListenerHandle>();

	private TreeViewer stagingViewer;

	@Override
	public void createPartControl(Composite parent) {
		stagingViewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL);
		stagingViewer.setLabelProvider(createLabelProvider());
		stagingViewer.setContentProvider(new ITreeContentProvider() {
			public void inputChanged(Viewer viewer, Object oldInput,
					Object newInput) {
			}

			public void dispose() {
			}

			public Object[] getElements(Object inputElement) {
				return (Object[]) inputElement;
			}

			public boolean hasChildren(Object element) {
				if (element instanceof Repository)
					return true;
				else if (element instanceof IFile)
					return false;
				else if (element instanceof StagedNode) {
					Set<IFile> files = staged.get(((StatusNode) element)
							.getRepository());
					return files == null ? false : !files.isEmpty();
				} else if (element instanceof ModifiedNode) {
					Set<IFile> files = modified.get(((StatusNode) element)
							.getRepository());
					return files == null ? false : !files.isEmpty();
				} else if (element instanceof UntrackedNode) {
					Set<IFile> files = untracked.get(((StatusNode) element)
							.getRepository());
					return files == null ? false : !files.isEmpty();
				} else if (element instanceof ResourceNode) {
					ResourceNode node = (ResourceNode) element;
					IResource resource = node.getResource();
					Set<IFile> files = node.getRoot().getFiles();
					for (IFile file : files)
						if (resource.contains(file))
							return true;
					return false;
				}
				return getChildren(element).length != 0;
			}

			public Object getParent(Object element) {
				if (element instanceof StatusNode)
					return ((StatusNode) element).getRepository();
				return null;
			}

			public Object[] getChildren(Object parentElement) {
				if (parentElement instanceof Repository) {
					Repository repository = (Repository) parentElement;
					return new Object[] { new ConflictingNode(repository),
							new StagedNode(repository),
							new ModifiedNode(repository),
							new UntrackedNode(repository) };
				} else if (parentElement instanceof ConflictingNode)
					return getNodeChildren((StatusNode) parentElement, conflicts);
				else if (parentElement instanceof StagedNode)
					return getNodeChildren((StatusNode) parentElement, staged);
				else if (parentElement instanceof ModifiedNode)
					return getNodeChildren((StatusNode) parentElement, modified);
				else if (parentElement instanceof UntrackedNode)
					return getNodeChildren((StatusNode) parentElement,
							untracked);
				else if (parentElement instanceof ResourceNode) {
					ResourceNode node = (ResourceNode) parentElement;
					IResource resource = node.getResource();
					Set<IFile> children = new HashSet<IFile>();
					Set<IFile> files = node.getRoot().getFiles();
					for (IFile file : files)
						if (resource.contains(file))
							children.add(file);

					List<Object> nodeChildren = getNodeChildren(node,
							node.getRoot(), resource, children);
					return collapse(nodeChildren);
				}
				return new Object[0];
			}
		});

		stagingViewer.setComparator(new ViewerComparator(
				new Comparator<String>() {
					public int compare(String o1, String o2) {
						return o1.compareToIgnoreCase(o2);
					}
				}) {
			@Override
			public int category(Object element) {
				if (element instanceof IFile)
					return 4;
				else if (element instanceof ResourceNode)
					return 3;
				else if (element instanceof UntrackedNode)
					return 2;
				else if (element instanceof ModifiedNode)
					return 1;
				return 0;
			}
		});

		Map<Repository, Set<IProject>> versionedProjects = createInput();
		Set<Repository> repositories = versionedProjects.keySet();
		stagingViewer.setInput(repositories.toArray());
		scheduleRepositoryAnalysis(versionedProjects, true);

		for (Repository repository : repositories)
			displayedRepositories.add(repository);

		MenuManager manager = new MenuManager();
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		Menu menu = manager.createContextMenu(stagingViewer.getControl());
		stagingViewer.getControl().setMenu(menu);
		getSite().registerContextMenu(manager, stagingViewer);
		getSite().setSelectionProvider(stagingViewer);

		attachListeners(repositories);
	}

	private DecoratingStyledCellLabelProvider createLabelProvider() {
		ILabelDecorator decorator = getSite().getPage().getWorkbenchWindow()
				.getWorkbench().getDecoratorManager().getLabelDecorator();
		return new DecoratingStyledCellLabelProvider(
				new StagingLabelProvider(), decorator, null);
	}

	private Map<Repository, Set<IProject>> createInput() {
		Map<Repository, Set<IProject>> versionedProjects = new HashMap<Repository, Set<IProject>>();
		for (IProject project : ResourcesPlugin.getWorkspace().getRoot()
				.getProjects()) {
			RepositoryMapping mapping = RepositoryMapping.getMapping(project);
			if (mapping != null) {
				Repository repository = mapping.getRepository();
				Set<IProject> projects = versionedProjects.get(repository);
				if (projects == null) {
					projects = new HashSet<IProject>();
					versionedProjects.put(repository, projects);
				}
				projects.add(project);
			}
		}
		return versionedProjects;
	}

	private void attachListeners(Collection<Repository> repositories) {
		for (Repository repository : repositories) {
			myListeners.add(repository.getListenerList()
					.addIndexChangedListener(myIndexChangedListener));
			myListeners.add(repository.getListenerList()
					.addRefsChangedListener(myRefsChangedListener));
		}

		stagingViewer.addOpenListener(new IOpenListener() {
			public void open(OpenEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event
						.getSelection();
				if (selection.isEmpty())
					return;

				Object element = selection.getFirstElement();
				ITreeContentProvider provider = (ITreeContentProvider) stagingViewer
						.getContentProvider();
				if (provider.hasChildren(element))
					stagingViewer.setExpandedState(element,
							!stagingViewer.getExpandedState(element));
				else {
					for (Object selectedElement : selection.toArray())
						if (!(selectedElement instanceof IFile))
							return;

					TreeItem[] selectedItems = stagingViewer.getTree()
							.getSelection();
					for (TreeItem selectedItem : selectedItems) {
						final IFile file = (IFile) selectedItem.getData();
						TreeItem parentItem = selectedItem.getParentItem();
						ResourceNode resourceNode = (ResourceNode) parentItem
								.getData();
						StatusNode data = resourceNode.getRoot();

						ITypedElement left = new EditableRevision(new LocalFileRevision(file)) {
							@Override
							public void setContent(final byte[] newContent) {
								try {
									PlatformUI.getWorkbench().getProgressService().run(
											false, false, new IRunnableWithProgress() {
												public void run(IProgressMonitor myMonitor)
														throws InvocationTargetException,
														InterruptedException {
													try {
														file.setContents(
																new ByteArrayInputStream(
																		newContent), false,
																true, myMonitor);
													} catch (CoreException e) {
														throw new InvocationTargetException(
																e);
													}
												}
											});
								} catch (InvocationTargetException e) {
									Activator.handleError(e.getTargetException()
											.getMessage(), e.getTargetException(), true);
								} catch (InterruptedException e) {
								}
							}
						};

						final String gitPath = RepositoryMapping.getMapping(
								file.getProject()).getRepoRelativePath(file);

						ITypedElement right;

						if (data instanceof StagedNode
								|| data instanceof UntrackedNode) {
							try {
								Ref head = data.getRepository().getRef(Constants.HEAD);
								RevWalk rw = new RevWalk(data.getRepository());
								RevCommit commit = rw.parseCommit(head.getObjectId());

								right = CompareUtils.getFileRevisionTypedElement(gitPath,
										commit, data.getRepository());
							} catch (IOException e) {
								Activator.handleError(e.getMessage(), e, true);
								return;
							}
						} else {
							try {
								right = getHeadTypedElement(file);
							} catch (IOException e) {
								Activator.handleError(
										UIText.CompareWithIndexAction_errorOnAddToIndex, e,
										true);
								return;
							}
						}
						GitCompareFileRevisionEditorInput compareInput = new GitCompareFileRevisionEditorInput(
								left, right, PlatformUI.getWorkbench()
										.getActiveWorkbenchWindow().getActivePage());
						CompareUtils.openInCompare(PlatformUI.getWorkbench()
								.getActiveWorkbenchWindow().getActivePage(), compareInput);
					}
				}
			}
		});

		stagingViewer.addDragSupport(DND.DROP_MOVE,
				new Transfer[] { LocalSelectionTransfer.getTransfer() },
				new DragSourceAdapter() {
					public void dragStart(DragSourceEvent event) {
						IStructuredSelection selection = (IStructuredSelection) stagingViewer
								.getSelection();
						event.doit = !selection.isEmpty()
								&& validateDragSource(selection.toArray());
					}
				});

		stagingViewer.addDropSupport(DND.DROP_MOVE,
				new Transfer[] { LocalSelectionTransfer.getTransfer() },
				new DropTargetAdapter() {
					public void drop(DropTargetEvent event) {
						final IStructuredSelection selection = (IStructuredSelection) stagingViewer
								.getSelection();
						final Repository repository = RepositoryMapping
								.getMapping((IFile) selection.getFirstElement())
								.getRepository();
						Set<IFile> modifiedFiles = modified.get(repository);

						List<IFile> modifiedTargets = new ArrayList<IFile>();
						List<IFile> untrackedTargets = new ArrayList<IFile>();

						for (Object object : selection.toArray())
							if (modifiedFiles.contains(object))
								modifiedTargets.add((IFile) object);
							else
								untrackedTargets.add((IFile) object);

						stage(repository, modifiedTargets, untrackedTargets);
					}

					public void dragOver(DropTargetEvent event) {
						event.detail = validateDropTarget(event.item.getData());
					}
				});

		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
	}

	private ITypedElement getHeadTypedElement(final IFile baseFile)
		throws IOException {
		final RepositoryMapping mapping = RepositoryMapping.getMapping(baseFile
				.getProject());
		final Repository repository = mapping.getRepository();
		final String gitPath = mapping.getRepoRelativePath(baseFile);

		DirCache dc = repository.lockDirCache();
		final DirCacheEntry entry = dc.getEntry(gitPath);
		dc.unlock();
		if (entry == null) {
			return new GitCompareFileRevisionEditorInput.EmptyTypedElement(NLS
					.bind(UIText.CompareWithIndexAction_FileNotInIndex,
							baseFile.getName()));
		}

		IFileRevision nextFile = GitFileRevision.inIndex(repository, gitPath);
		final EditableRevision next = new EditableRevision(nextFile);

		IContentChangeListener listener = new IContentChangeListener() {
			public void contentChanged(IContentChangeNotifier source) {
				final byte[] newContent = next.getModifiedContent();
				DirCache cache = null;
				try {
					cache = repository.lockDirCache();
					DirCacheEditor editor = cache.editor();
					editor.add(new PathEdit(gitPath) {
						@Override
						public void apply(DirCacheEntry ent) {
							ent.copyMetaData(entry);

							ObjectInserter inserter = repository
									.newObjectInserter();
							ent.copyMetaData(entry);
							ent.setLength(newContent.length);
							ent.setLastModified(System.currentTimeMillis());
							InputStream in = new ByteArrayInputStream(
									newContent);
							try {
								ent.setObjectId(inserter.insert(
										Constants.OBJ_BLOB, newContent.length,
										in));
								inserter.flush();
							} catch (IOException ex) {
								throw new RuntimeException(ex);
							} finally {
								try {
									in.close();
								} catch (IOException e) {
								}
							}
						}
					});
					try {
						editor.commit();
					} catch (RuntimeException e) {
						if (e.getCause() instanceof IOException)
							throw (IOException) e.getCause();
						else
							throw e;
					}

				} catch (IOException e) {
					Activator.handleError(
							UIText.CompareWithIndexAction_errorOnAddToIndex, e,
							true);
				} finally {
					if (cache != null)
						cache.unlock();
				}
			}
		};

		next.addContentChangeListener(listener);
		return next;
	}

	@Override
	public void dispose() {
		for (ListenerHandle lh : myListeners)
			lh.remove();
		myListeners.clear();

		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		super.dispose();
	}

	@Override
	public void setFocus() {
		stagingViewer.getControl().setFocus();
	}

	private void stage(final Repository repository,
			final List<IFile> modifiedFiles, final List<IFile> untrackedFiles) {
		final Collection<IFile> targets = new ArrayList<IFile>(modifiedFiles);
		targets.addAll(untrackedFiles);

		Job job = new Job(UIText.StagingView_StagingJobLabel) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				SubMonitor subMonitor = SubMonitor.convert(monitor);
				if (subMonitor.isCanceled())
					return Status.CANCEL_STATUS;

				subMonitor.beginTask(NLS.bind(
						UIText.StagingView_StagingJobTaskName, repository
								.getDirectory().getParentFile().getName()),
						modifiedFiles.size() + untrackedFiles.size());

				AddToIndexOperation indexOperation = new AddToIndexOperation(
						modifiedFiles);

				try {
					indexOperation.execute(subMonitor.newChild(
							modifiedFiles.size(), SubMonitor.SUPPRESS_NONE));
					if (subMonitor.isCanceled()) {
						reload(repository, modifiedFiles);
						return Status.CANCEL_STATUS;
					}

					modified.get(repository).removeAll(modifiedFiles);
					untracked.get(repository).removeAll(untrackedFiles);

					staged.get(repository).addAll(modifiedFiles);
					staged.get(repository).addAll(untrackedFiles);

					refreshUI(repository);
				} catch (CoreException e) {
					return new Status(IStatus.ERROR, Activator.getPluginId(),
							UIText.StagingView_FailedToStageFiles, e);
				} finally {
					subMonitor.done();
				}
				return Status.OK_STATUS;
			}
		};

		job.setRule(new MultiRule(targets.toArray(new IFile[targets.size()])));
		job.schedule();
	}

	private int validateDropTarget(Object data) {
		if (data instanceof StagedNode) {
			IStructuredSelection selection = (IStructuredSelection) stagingViewer
					.getSelection();
			Repository dragSourceRepository = RepositoryMapping.getMapping(
					(IFile) selection.getFirstElement()).getRepository();
			if (dragSourceRepository == ((StagedNode) data).getRepository())
				return DND.DROP_MOVE;
		}
		return DND.DROP_NONE;
	}

	private Object[] getNodeChildren(StatusNode node,
			Map<Repository, Set<IFile>> map) {
		Repository repository = node.getRepository();
		Set<IFile> set = map.get(repository);
		node.setFiles(set);

		Set<IProject> projects = new HashSet<IProject>();
		for (IFile file : set)
			projects.add(file.getProject());

		ResourceNode[] nodes = new ResourceNode[projects.size()];
		int i = 0;
		for (IProject project : projects) {
			nodes[i] = new ResourceNode(node, project, null);
			i++;
		}
		return nodes;
	}

	private List<Object> getNodeChildren(ResourceNode parentNode,
			StatusNode root, IResource resource, Set<IFile> files) {
		List<Object> children = new ArrayList<Object>();
		for (IFile file : files) {
			IResource parentResource = file.getParent();
			if (parentResource.equals(resource))
				children.add(file);
			else
				children.add(new ResourceNode(root, parentResource, parentNode));
		}
		return children;
	}

	private Object[] collapse(List<Object> children) {
		for (int i = 0; i < children.size(); i++) {
			Object child = children.get(i);
			if (child instanceof ResourceNode) {
				IResource intermediate = ((ResourceNode) child).getResource();

				for (int j = i + 1; j < children.size(); j++) {
					child = children.get(j);
					if (child instanceof ResourceNode) {
						IResource intermediate2 = ((ResourceNode) child)
								.getResource();
						if (intermediate.contains(intermediate2)) {
							children.remove(j);
							i--;
							j--;
							break;
						} else if (intermediate2.contains(intermediate)) {
							children.remove(i);
							i--;
							j--;
							break;
						}
					}
				}
			}
		}

		return children.toArray();
	}

	private void refreshUI(final Repository repository) {
		WorkbenchJob job = new WorkbenchJob("") { //$NON-NLS-1$
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				if (!stagingViewer.getTree().isDisposed()) {
					TreeItem[] items = stagingViewer.getTree().getItems();
					for (TreeItem item : items) {
						if (item.getData() == repository) {
							stagingViewer.refresh(repository);
							stagingViewer.expandToLevel(repository, 2);
							return Status.OK_STATUS;
						}
					}

					displayedRepositories.add(repository);
					myListeners.add(repository.getListenerList()
							.addIndexChangedListener(myIndexChangedListener));
					myListeners.add(repository.getListenerList()
							.addRefsChangedListener(myRefsChangedListener));

					stagingViewer.add(stagingViewer.getInput(), repository);
					stagingViewer.expandToLevel(repository, 2);
				}
				return Status.OK_STATUS;
			}
		};

		IWorkbenchSiteProgressService progressService = (IWorkbenchSiteProgressService) getSite()
				.getService(IWorkbenchSiteProgressService.class);
		progressService.schedule(job);
	}

	private boolean validateDragSource(Object[] array) {
		for (Object object : array)
			if (!(object instanceof IFile))
				return false;

		IFile firstFile = (IFile) array[0];
		Repository repository = RepositoryMapping.getMapping(firstFile)
				.getRepository();
		for (int i = 1; i < array.length; i++) {
			IFile file = (IFile) array[i];
			if (repository != RepositoryMapping.getMapping(file)
					.getRepository())
				return false;
		}

		Set<IFile> modifiedFiles = modified.get(repository);
		Set<IFile> untrackedFiles = untracked.get(repository);
		if (modifiedFiles.isEmpty() && untrackedFiles.isEmpty())
			return false;

		for (Object object : array) {
			IFile file = (IFile) object;
			if (!modifiedFiles.contains(file) && !untrackedFiles.contains(file))
				return false;
		}

		return true;
	}

	void scheduleRepositoryAnalysis(
			final Map<Repository, Set<IProject>> versionedProjects,
			final boolean full) {
		Job job = new Job(UIText.StagingView_AnalyzeJobLabel) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				SubMonitor subMonitor = SubMonitor.convert(monitor);
				if (subMonitor.isCanceled())
					return Status.CANCEL_STATUS;

				try {
					int workRemaining = versionedProjects.size();
					subMonitor.beginTask(UIText.StagingView_AnalyzeJobTaskName,
							workRemaining);

					for (Map.Entry<Repository, Set<IProject>> entry : versionedProjects
							.entrySet()) {
						Set<IProject> projects = entry.getValue();
						if (subMonitor.isCanceled())
							return Status.CANCEL_STATUS;
						else if (projects.isEmpty()) {
							workRemaining--;
							subMonitor.setWorkRemaining(workRemaining);
							continue;
						}

						Repository repo = entry.getKey();
						reload(repo, projects, full, subMonitor.newChild(1,
								SubMonitor.SUPPRESS_NONE));
						refreshUI(repo);

						workRemaining--;
					}
				} finally {
					subMonitor.done();
				}
				return Status.OK_STATUS;
			}
		};

		Set<IProject> collectedProjects = new HashSet<IProject>();
		for (Set<IProject> projects : versionedProjects.values())
			collectedProjects.addAll(projects);
		job.setRule(new MultiRule(collectedProjects
				.toArray(new IProject[collectedProjects.size()])));

		IWorkbenchSiteProgressService progressService = (IWorkbenchSiteProgressService) getSite()
				.getService(IWorkbenchSiteProgressService.class);
		progressService.schedule(job, 0, true);
	}

	private void reload(Repository repository) {
		Set<IProject> projects = new HashSet<IProject>();
		for (IProject project : ResourcesPlugin.getWorkspace().getRoot()
				.getProjects()) {
			RepositoryMapping mapping = RepositoryMapping.getMapping(project);
			if (mapping != null && mapping.getRepository() == repository)
				projects.add(project);
		}

		scheduleRepositoryAnalysis(
				Collections.singletonMap(repository, projects), true);
	}

	private void reload(Repository repository, Collection<IFile> files) {
		Set<IProject> projects = new HashSet<IProject>();
		for (IFile file : files)
			projects.add(file.getProject());
		scheduleRepositoryAnalysis(
				Collections.singletonMap(repository, projects), false);
	}

	private void reload(final Repository repository, Set<IProject> projects,
			boolean full, IProgressMonitor progressMonitor) {
		SubMonitor subMonitor = SubMonitor.convert(
				progressMonitor,
				NLS.bind(UIText.StagingView_AnalyzeJobSubTaskName, repository
						.getDirectory().getParentFile().getName()),
				projects.size() * 2);
		if (full) {
			staged.put(repository, new HashSet<IFile>());
			modified.put(repository, new HashSet<IFile>());
			untracked.put(repository, new HashSet<IFile>());
			conflicts.put(repository, new HashSet<IFile>());
		} else
			filterCache(repository, projects);

		try {
			IndexDiff indexDiff = new IndexDiff(repository, Constants.HEAD,
					IteratorService.createInitialIterator(repository));
			indexDiff.diff(null /*subMonitor*/, projects.size(), 0, NLS.bind(
					UIText.CommitActionHandler_repository, repository
							.getDirectory().getPath()));

			for (IProject project : projects) {
				processFiles(project, indexDiff.getAdded(), staged, repository);
				processFiles(project, indexDiff.getRemoved(), staged, repository);
				processFiles(project, indexDiff.getChanged(), staged, repository);
				processFiles(project, indexDiff.getModified(), modified, repository);
				processFiles(project, indexDiff.getMissing(), untracked, repository);
				processFiles(project, indexDiff.getUntracked(), untracked, repository);
				processFiles(project, indexDiff.getConflicting(), conflicts, repository);
			}
		} catch (IOException e) {
			Activator
					.getDefault()
					.getLog()
					.log(new Status(IStatus.ERROR, Activator.getPluginId(),
							"Error occurred while reading Git index", e)); //$NON-NLS-1$
		} finally {
			subMonitor.done();
		}
	}

	private void processFiles(IProject project, Set<String> added,
			Map<Repository, Set<IFile>> category, Repository repository) {
		String repoRelativePath = RepositoryMapping.getMapping(project).getRepoRelativePath(project);
		if (repoRelativePath.length() > 0)
			repoRelativePath += "/"; //$NON-NLS-1$

		for (String filename : added) {
			try {
				if (!filename.startsWith(repoRelativePath))
					continue;
				String projectRelativePath = filename
						.substring(repoRelativePath.length());
				IFile member = project.getFile(projectRelativePath);
				category.get(repository).add(member);
			} catch (Exception e) {
				if (GitTraceLocation.UI.isActive())
					GitTraceLocation.getTrace().trace(
							GitTraceLocation.UI.getLocation(), e.getMessage(),
							e);
				continue;
			} // if it's outside the workspace, bad things happen
		}
	}

	private void filterCache(Repository repository, Set<IProject> projects) {
		Set<IFile> files = staged.get(repository);
		if (files == null)
			staged.put(repository, new HashSet<IFile>());
		else
			for (IProject project : projects)
				for (Iterator<IFile> it = files.iterator(); it.hasNext();)
					if (project.contains(it.next()))
						it.remove();

		files = modified.get(repository);
		if (files == null)
			modified.put(repository, new HashSet<IFile>());
		else
			for (IProject project : projects)
				for (Iterator<IFile> it = files.iterator(); it.hasNext();)
					if (project.contains(it.next()))
						it.remove();

		files = untracked.get(repository);
		if (files == null)
			untracked.put(repository, new HashSet<IFile>());
		else
			for (IProject project : projects)
				for (Iterator<IFile> it = files.iterator(); it.hasNext();)
					if (project.contains(it.next()))
						it.remove();

		files = conflicts.get(repository);
		if (files == null)
			conflicts.put(repository, new HashSet<IFile>());
		else
			for (IProject project : projects)
				for (Iterator<IFile> it = files.iterator(); it.hasNext();)
					if (project.contains(it.next()))
						it.remove();
	}

	public void resourceChanged(IResourceChangeEvent event) {
		switch (event.getType()) {
		case IResourceChangeEvent.PRE_CLOSE:
		case IResourceChangeEvent.PRE_DELETE:
			handleRemoval(event);
			break;
		case IResourceChangeEvent.POST_CHANGE:
			try {
				handleChange(event);
			} catch (CoreException e) {
				Activator
						.getDefault()
						.getLog()
						.log(new Status(IStatus.ERROR, Activator.getPluginId(),
								"Error processing resource delta", e)); //$NON-NLS-1$
			}
			break;
		}
	}

	private void handleChange(IResourceChangeEvent event) throws CoreException {
		final Map<IProject, Boolean> ignoredProjects = new HashMap<IProject, Boolean>();
		final Map<Repository, Set<IProject>> changedRepositories = new HashMap<Repository, Set<IProject>>();
		event.getDelta().accept(new IResourceDeltaVisitor() {
			public boolean visit(IResourceDelta delta) throws CoreException {
				IResource deltaResource = delta.getResource();
				switch (deltaResource.getType()) {
				case IResource.PROJECT:
					RepositoryMapping mapping = RepositoryMapping
							.getMapping(deltaResource);
					if (mapping == null)
						return false;

					Repository repository = mapping.getRepository();
					Set<IProject> projects = changedRepositories
							.get(repository);
					if (projects == null) {
						projects = new HashSet<IProject>();
						changedRepositories.put(repository, projects);
					}
					projects.add((IProject) deltaResource);

					if ((delta.getFlags() & IResourceDelta.OPEN) != 0)
						return false;
					return true;
				case IResource.FILE:
					IProject project = deltaResource.getProject();
					if ((delta.getFlags() & IResourceDelta.MARKERS) != 0) {
						Boolean ignored = ignoredProjects.get(project);
						ignoredProjects.put(deltaResource.getProject(),
								ignored == null ? Boolean.TRUE : ignored);
					} else
						ignoredProjects.put(deltaResource.getProject(),
								Boolean.FALSE);
				}
				return true;
			}
		});

		for (Set<IProject> projects : changedRepositories.values()) {
			for (Map.Entry<IProject, Boolean> ignored : ignoredProjects
					.entrySet()) {
				IProject project = ignored.getKey();
				if (ignored.getValue().booleanValue()
						&& projects.contains(project))
					projects.remove(project);
			}
		}

		scheduleRepositoryAnalysis(changedRepositories, false);
	}

	private void handleRemoval(IResourceChangeEvent event) {
		final IResource resource = event.getResource();
		RepositoryMapping mapping = RepositoryMapping.getMapping(resource);
		if (mapping != null) {
			final Repository repository = mapping.getRepository();
			remove(repository, resource);

			stagingViewer.getTree().getDisplay().asyncExec(new Runnable() {
				public void run() {
					if (!stagingViewer.getTree().isDisposed()) {
						TreeItem[] items = stagingViewer.getTree().getItems();
						for (TreeItem item : items)
							if (item.getData() == repository) {
								disposeItems(resource, item);
								break;
							}
					}
				}
			});
		}
	}

	private void remove(Repository repository, IResource resource) {
		Iterator<IFile> iterator = staged.get(repository).iterator();
		while (iterator.hasNext())
			if (resource.contains(iterator.next()))
				iterator.remove();

		iterator = modified.get(repository).iterator();
		while (iterator.hasNext())
			if (resource.contains(iterator.next()))
				iterator.remove();

		iterator = untracked.get(repository).iterator();
		while (iterator.hasNext())
			if (resource.contains(iterator.next()))
				iterator.remove();
	}

	private void disposeItems(IResource resource, TreeItem item) {
		Object data = item.getData();
		if (data instanceof ResourceNode
				&& resource == ((ResourceNode) data).getResource()) {
			item.dispose();
			return;
		}

		for (TreeItem child : item.getItems())
			disposeItems(resource, child);
	}

}
