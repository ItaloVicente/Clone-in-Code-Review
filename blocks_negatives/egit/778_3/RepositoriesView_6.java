public class RepositoriesView extends ViewPart implements ISelectionProvider,
		IShowInTarget {

	/** The view ID */








	private final Set<Repository> repositories = new HashSet<Repository>();

	private final List<ISelectionChangedListener> selectionListeners = new ArrayList<ISelectionChangedListener>();

	private RepositoryListener repositoryListener;

	private ISelection currentSelection = new StructuredSelection();

	private Job scheduledJob;

	private TreeViewer tv;

	private IAction importAction;

	private IAction addAction;

	private IAction refreshAction;

	private IAction linkWithSelectionAction;

	private IAction copyAction;

	private IAction pasteAction;

	/**
	 * TODO move to utility class
	 *
	 * @return the directories as configured for this view
	 */
	public static List<String> getDirs() {
		List<String> resultStrings = new ArrayList<String>();
		String dirs = getPrefs().get(PREFS_DIRECTORIES, ""); //$NON-NLS-1$
		if (dirs != null && dirs.length() > 0) {
			StringTokenizer tok = new StringTokenizer(dirs, File.pathSeparator);
			while (tok.hasMoreTokens()) {
				String dirName = tok.nextToken();
				File testFile = new File(dirName);
				if (testFile.exists() && !resultStrings.contains(dirName)) {
					resultStrings.add(dirName);
				}
			}
		}
		Collections.sort(resultStrings);
		return resultStrings;
	}

	private static void removeDir(File file) {

		String dir;
		try {
			dir = file.getCanonicalPath();
		} catch (IOException e1) {
			dir = file.getAbsolutePath();
		}

		IEclipsePreferences prefs = getPrefs();

		TreeSet<String> resultStrings = new TreeSet<String>();
		String dirs = prefs.get(PREFS_DIRECTORIES, ""); //$NON-NLS-1$
		if (dirs != null && dirs.length() > 0) {
			StringTokenizer tok = new StringTokenizer(dirs, File.pathSeparator);
			while (tok.hasMoreTokens()) {
				String dirName = tok.nextToken();
				File testFile = new File(dirName);
				if (testFile.exists()) {
					try {
						resultStrings.add(testFile.getCanonicalPath());
					} catch (IOException e) {
						resultStrings.add(testFile.getAbsolutePath());
					}
				}
			}
		}

		if (resultStrings.remove(dir)) {
			StringBuilder sb = new StringBuilder();
			for (String gitDirString : resultStrings) {
				sb.append(gitDirString);
				sb.append(File.pathSeparatorChar);
			}

			prefs.put(PREFS_DIRECTORIES, sb.toString());
			try {
				prefs.flush();
			} catch (BackingStoreException e) {
				IStatus error = new Status(IStatus.ERROR, Activator
						.getPluginId(), e.getMessage(), e);
				Activator.getDefault().getLog().log(error);
			}
		}

	}

	@Override
	public Object getAdapter(Class adapter) {
		if (adapter == IPropertySheetPage.class) {
			PropertySheetPage page = new PropertySheetPage();
			page
					.setPropertySourceProvider(new RepositoryPropertySourceProvider(
							page));
			return page;
		}

		return super.getAdapter(adapter);
	}

	@Override
	public void createPartControl(Composite parent) {
		tv = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		tv.setContentProvider(new RepositoriesViewContentProvider());
		new RepositoriesViewLabelProvider(tv);

		getSite().setSelectionProvider(this);

		tv.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {

				copyAction.setEnabled(false);

				IStructuredSelection ssel = (IStructuredSelection) event
						.getSelection();
				if (ssel.size() == 1) {
					RepositoryTreeNode node = (RepositoryTreeNode) ssel
							.getFirstElement();
					if (node.getType() == RepositoryTreeNodeType.REPO
							|| node.getType() == RepositoryTreeNodeType.WORKINGDIR
							|| node.getType() == RepositoryTreeNodeType.FOLDER
							|| node.getType() == RepositoryTreeNodeType.FILE) {
						copyAction.setEnabled(true);
					}
					setSelection(new StructuredSelection(ssel.getFirstElement()));
				} else {
					setSelection(new StructuredSelection());
				}

			}
		});
		tv.addOpenListener(new IOpenListener() {
			public void open(OpenEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event
						.getSelection();
				if (selection.isEmpty()) {
					return;
				}

				Object element = selection.getFirstElement();
				ITreeContentProvider contentProvider = (ITreeContentProvider) tv
						.getContentProvider();
				if (contentProvider.hasChildren(element)) {
					tv.setExpandedState(element, !tv.getExpandedState(element));
				} else {
					Object[] selectionArray = selection.toArray();
					for (Object selectedElement : selectionArray) {
						RepositoryTreeNode node = (RepositoryTreeNode) selectedElement;
						if (node.getType() != RepositoryTreeNodeType.FILE
								&& node.getType() != RepositoryTreeNodeType.REF
								&& node.getType() != RepositoryTreeNodeType.TAG) {
							return;
						}
					}

					for (Object selectedElement : selectionArray) {
						RepositoryTreeNode node = (RepositoryTreeNode) selectedElement;
						if (node.getType() == RepositoryTreeNodeType.FILE)
							openFile((File) node.getObject());
						else if (node.getType() == RepositoryTreeNodeType.REF
								|| node.getType() == RepositoryTreeNodeType.TAG) {
							Ref ref = (Ref) node.getObject();
							if (!isBare(node.getRepository())
									&& ref.getName().startsWith(
											Constants.R_REFS))
								checkoutBranch(node, ref.getName());
						}
					}
				}
			}
		});

		createRepositoryChangedListener();

		addContextMenu();

		addActionsToToolbar();

		scheduleRefresh();

		ISelectionService srv = (ISelectionService) getSite().getService(
				ISelectionService.class);
		srv.addPostSelectionListener(new ISelectionListener() {

			public void selectionChanged(IWorkbenchPart part,
					ISelection selection) {

				if (linkWithSelectionAction == null
						|| !linkWithSelectionAction.isChecked())
					return;

				if (part instanceof IEditorPart) {
					IEditorInput input = ((IEditorPart) part).getEditorInput();
					if (input instanceof IFileEditorInput)
						reactOnSelection(new StructuredSelection(
								((IFileEditorInput) input).getFile()));

				} else {
					reactOnSelection(selection);
				}
			}

		});
	}

	private void reactOnSelection(ISelection selection) {
		if (selection instanceof StructuredSelection) {
			StructuredSelection ssel = (StructuredSelection) selection;
			if (ssel.size() != 1)
				return;
			if (ssel.getFirstElement() instanceof IResource) {
				showResource((IResource) ssel.getFirstElement());
			}
			if (ssel.getFirstElement() instanceof IAdaptable) {
				IResource adapted = (IResource) ((IAdaptable) ssel
						.getFirstElement()).getAdapter(IResource.class);
				if (adapted != null)
					showResource(adapted);
			}
		}
	}

	private void addContextMenu() {
		tv.getTree().addMenuDetectListener(new MenuDetectListener() {

			public void menuDetected(MenuDetectEvent e) {
				Menu men = tv.getTree().getMenu();
				if (men != null) {
					men.dispose();
				}
				men = new Menu(tv.getTree());

				TreeItem testItem = tv.getTree().getItem(
						tv.getTree().toControl(new Point(e.x, e.y)));
				if (testItem == null) {
					addMenuItemsForPanel(men);
				} else {
					addMenuItemsForTreeSelection(men);
				}

				tv.getTree().setMenu(men);
			}
		});
	}

	private void addMenuItemsForPanel(Menu men) {

		MenuItem importItem = new MenuItem(men, SWT.PUSH);
		importItem.setText(UIText.RepositoriesView_ImportRepository_MenuItem);
		importItem.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				importAction.run();
			}

		});

		MenuItem addItem = new MenuItem(men, SWT.PUSH);
		addItem.setText(UIText.RepositoriesView_AddRepository_MenuItem);
		addItem.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				addAction.run();
			}

		});

		MenuItem pasteItem = new MenuItem(men, SWT.PUSH);
		pasteItem.setText(UIText.RepositoriesView_PasteMenu);
		pasteItem.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				pasteAction.run();
			}

		});

		MenuItem refreshItem = new MenuItem(men, SWT.PUSH);
		refreshItem.setText(refreshAction.getText());
		refreshItem.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				refreshAction.run();
			}

		});

	}

	private void addMenuItemsForTreeSelection(Menu men) {

		final IStructuredSelection sel = (IStructuredSelection) tv
				.getSelection();

		boolean repoOnly = true;
		for (Object selected : sel.toArray()) {

			if (((RepositoryTreeNode) selected).getType() != RepositoryTreeNodeType.REPO) {
				repoOnly = false;
				break;
			}
		}

		if (sel.size() > 1 && repoOnly) {
			List nodes = sel.toList();
			final Repository[] repos = new Repository[nodes.size()];
			for (int i = 0; i < sel.size(); i++)
				repos[i] = ((RepositoryTreeNode) nodes.get(i)).getRepository();

			MenuItem remove = new MenuItem(men, SWT.PUSH);
			remove.setText(UIText.RepositoriesView_Remove_MenuItem);
			remove.addSelectionListener(new SelectionAdapter() {
