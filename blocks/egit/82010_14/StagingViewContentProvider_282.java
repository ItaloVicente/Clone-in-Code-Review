package org.eclipse.egit.ui.internal.staging;

import static org.eclipse.egit.ui.internal.CommonUtils.runCommand;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.egit.core.AdapterUtils;
import org.eclipse.egit.core.RepositoryUtil;
import org.eclipse.egit.core.internal.gerrit.GerritUtil;
import org.eclipse.egit.core.internal.indexdiff.IndexDiffCacheEntry;
import org.eclipse.egit.core.internal.indexdiff.IndexDiffChangedListener;
import org.eclipse.egit.core.internal.indexdiff.IndexDiffData;
import org.eclipse.egit.core.internal.job.RuleUtil;
import org.eclipse.egit.core.op.CommitOperation;
import org.eclipse.egit.core.project.RepositoryMapping;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.JobFamilies;
import org.eclipse.egit.ui.UIPreferences;
import org.eclipse.egit.ui.UIUtils;
import org.eclipse.egit.ui.internal.CommonUtils;
import org.eclipse.egit.ui.internal.GitLabels;
import org.eclipse.egit.ui.internal.UIIcons;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.egit.ui.internal.actions.ActionCommands;
import org.eclipse.egit.ui.internal.actions.BooleanPrefAction;
import org.eclipse.egit.ui.internal.actions.ReplaceWithOursTheirsMenu;
import org.eclipse.egit.ui.internal.branch.LaunchFinder;
import org.eclipse.egit.ui.internal.commands.shared.AbortRebaseCommand;
import org.eclipse.egit.ui.internal.commands.shared.AbstractRebaseCommandHandler;
import org.eclipse.egit.ui.internal.commands.shared.ContinueRebaseCommand;
import org.eclipse.egit.ui.internal.commands.shared.SkipRebaseCommand;
import org.eclipse.egit.ui.internal.commit.CommitHelper;
import org.eclipse.egit.ui.internal.commit.CommitJob;
import org.eclipse.egit.ui.internal.commit.CommitJob.PushMode;
import org.eclipse.egit.ui.internal.commit.CommitMessageHistory;
import org.eclipse.egit.ui.internal.commit.CommitProposalProcessor;
import org.eclipse.egit.ui.internal.commit.DiffViewer;
import org.eclipse.egit.ui.internal.components.ToggleableWarningLabel;
import org.eclipse.egit.ui.internal.decorators.IProblemDecoratable;
import org.eclipse.egit.ui.internal.decorators.ProblemLabelDecorator;
import org.eclipse.egit.ui.internal.dialogs.CommitMessageArea;
import org.eclipse.egit.ui.internal.dialogs.CommitMessageComponent;
import org.eclipse.egit.ui.internal.dialogs.CommitMessageComponentState;
import org.eclipse.egit.ui.internal.dialogs.CommitMessageComponentStateManager;
import org.eclipse.egit.ui.internal.dialogs.ICommitMessageComponentNotifications;
import org.eclipse.egit.ui.internal.dialogs.SpellcheckableMessageArea;
import org.eclipse.egit.ui.internal.operations.DeletePathsOperationUI;
import org.eclipse.egit.ui.internal.operations.IgnoreOperationUI;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryTreeNode;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ControlContribution;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.DialogSettings;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.IPersistentPreferenceStore;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.ContentViewer;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeViewerListener;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeExpansionEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CheckoutCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand;
import org.eclipse.jgit.api.RmCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.events.ListenerHandle;
import org.eclipse.jgit.events.RefsChangedEvent;
import org.eclipse.jgit.events.RefsChangedListener;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryState;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.VerifyKeyListener;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IPartService;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IURIEditorInput;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.operations.UndoRedoActionGroup;
import org.eclipse.ui.part.IShowInSource;
import org.eclipse.ui.part.ShowInContext;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.progress.IWorkbenchSiteProgressService;

public class StagingView extends ViewPart implements IShowInSource {

	public static final String VIEW_ID = "org.eclipse.egit.ui.StagingView"; //$NON-NLS-1$

	private static final String EMPTY_STRING = ""; //$NON-NLS-1$

	private static final String SORT_ITEM_TOOLBAR_ID = "sortItem"; //$NON-NLS-1$

	private static final String EXPAND_ALL_ITEM_TOOLBAR_ID = "expandAllItem"; //$NON-NLS-1$

	private static final String COLLAPSE_ALL_ITEM_TOOLBAR_ID = "collapseAllItem"; //$NON-NLS-1$

	private static final String STORE_SORT_STATE = SORT_ITEM_TOOLBAR_ID
			+ "State"; //$NON-NLS-1$

	private static final String HORIZONTAL_SASH_FORM_WEIGHT = "HORIZONTAL_SASH_FORM_WEIGHT"; //$NON-NLS-1$

	private static final String STAGING_SASH_FORM_WEIGHT = "STAGING_SASH_FORM_WEIGHT"; //$NON-NLS-1$

	private ISelection initialSelection;

	private FormToolkit toolkit;

	private Form form;

	private SashForm mainSashForm;

	private Section stagedSection;

	private Section unstagedSection;

	private Section commitMessageSection;

	private TreeViewer stagedViewer;

	private TreeViewer unstagedViewer;

	private ToggleableWarningLabel warningLabel;

	private Text filterText;

	private SpellcheckableMessageArea commitMessageText;

	private Text committerText;

	private Text authorText;

	private CommitMessageComponent commitMessageComponent;

	private boolean reactOnSelection = true;

	private boolean isViewHidden;

	private ISelectionListener selectionChangedListener;

	private IPartListener2 partListener;

	private ToolBarManager unstagedToolBarManager;

	private ToolBarManager stagedToolBarManager;

	private Action listPresentationAction;

	private Action treePresentationAction;

	private Action compactTreePresentationAction;

	private Action unstagedExpandAllAction;

	private Action unstagedCollapseAllAction;

	private Action stagedExpandAllAction;

	private Action stagedCollapseAllAction;

	private Action compareModeAction;

	@Nullable
	private Repository currentRepository;

	private Presentation presentation = Presentation.LIST;

	private Set<IPath> pathsToExpandInStaged = new HashSet<>();

	private Set<IPath> pathsToExpandInUnstaged = new HashSet<>();

	public enum Presentation {
		LIST,
		TREE,
		COMPACT_TREE;
	}

	static class StagingViewUpdate {
		Repository repository;
		IndexDiffData indexDiff;
		Collection<String> changedResources;

		StagingViewUpdate(Repository theRepository,
				IndexDiffData theIndexDiff, Collection<String> theChanges) {
			this.repository = theRepository;
			this.indexDiff = theIndexDiff;
			this.changedResources = theChanges;
		}
	}

	private static class StagingDragSelection implements IStructuredSelection {

		private final IStructuredSelection delegate;

		private final boolean fromUnstaged;

		public StagingDragSelection(IStructuredSelection original,
				boolean fromUnstaged) {
			this.delegate = original;
			this.fromUnstaged = fromUnstaged;
		}

		@Override
		public boolean isEmpty() {
			return delegate.isEmpty();
		}

		@Override
		public Object getFirstElement() {
			return delegate.getFirstElement();
		}

		@Override
		public Iterator iterator() {
			return delegate.iterator();
		}

		@Override
		public int size() {
			return delegate.size();
		}

		@Override
		public Object[] toArray() {
			return delegate.toArray();
		}

		@Override
		public List toList() {
			return delegate.toList();
		}

		public boolean isFromUnstaged() {
			return fromUnstaged;
		}
	}

	private static class StagingDragListener extends DragSourceAdapter {

		private final ISelectionProvider provider;

		private final StagingViewContentProvider contentProvider;

		private final boolean unstaged;

		public StagingDragListener(ISelectionProvider provider,
				StagingViewContentProvider contentProvider, boolean unstaged) {
			this.provider = provider;
			this.contentProvider = contentProvider;
			this.unstaged = unstaged;
		}

		@Override
		public void dragStart(DragSourceEvent event) {
			event.doit = !provider.getSelection().isEmpty();
		}

		@Override
		public void dragFinished(DragSourceEvent event) {
			if (LocalSelectionTransfer.getTransfer().isSupportedType(
					event.dataType)) {
				LocalSelectionTransfer.getTransfer().setSelection(null);
			}
		}

		@Override
		public void dragSetData(DragSourceEvent event) {
			IStructuredSelection selection = (IStructuredSelection) provider
					.getSelection();
			if (selection.isEmpty()) {
				return;
			}
			if (LocalSelectionTransfer.getTransfer().isSupportedType(
					event.dataType)) {
				LocalSelectionTransfer.getTransfer().setSelection(
						new StagingDragSelection(selection, unstaged));
				return;
			}

			if (FileTransfer.getInstance().isSupportedType(event.dataType)) {
				Set<String> files = new HashSet<>();
				for (Object selected : selection.toList())
					if (selected instanceof StagingEntry) {
						add((StagingEntry) selected, files);
					} else if (selected instanceof StagingFolderEntry) {
						StagingFolderEntry folder = (StagingFolderEntry) selected;
						for (StagingEntry entry : contentProvider
								.getStagingEntriesFiltered(folder)) {
							add(entry, files);
						}
					}
				if (!files.isEmpty()) {
					event.data = files.toArray(new String[files.size()]);
					return;
				}
			}
		}

		private void add(StagingEntry entry, Collection<String> files) {
			File file = entry.getLocation().toFile();
			if (file.exists()) {
				files.add(file.getAbsolutePath());
			}
		}
	}

	private final class PartListener implements IPartListener2 {
		StructuredSelection lastSelection;

		@Override
		public void partVisible(IWorkbenchPartReference partRef) {
			updateHiddenState(partRef, false);
		}

		@Override
		public void partOpened(IWorkbenchPartReference partRef) {
			updateHiddenState(partRef, false);
		}

		@Override
		public void partHidden(IWorkbenchPartReference partRef) {
			updateHiddenState(partRef, true);
		}

		@Override
		public void partClosed(IWorkbenchPartReference partRef) {
			updateHiddenState(partRef, true);
		}

		@Override
		public void partActivated(IWorkbenchPartReference partRef) {
			if (isMe(partRef)) {
				if (lastSelection != null) {
					reactOnSelection(lastSelection);
					lastSelection = null;
				}
				return;
			}
			IWorkbenchPart part = partRef.getPart(false);
			StructuredSelection sel = getSelectionOfPart(part);
			if (isViewHidden) {
				lastSelection = sel;
			} else {
				lastSelection = null;
				if (sel != null) {
					reactOnSelection(sel);
				}
			}

		}

		private void updateHiddenState(IWorkbenchPartReference partRef,
				boolean hidden) {
			if (isMe(partRef)) {
				isViewHidden = hidden;
			}
		}

		private boolean isMe(IWorkbenchPartReference partRef) {
			return partRef.getPart(false) == StagingView.this;
		}

		@Override
		public void partDeactivated(IWorkbenchPartReference partRef) {
		}

		@Override
		public void partBroughtToTop(IWorkbenchPartReference partRef) {
		}

		@Override
		public void partInputChanged(IWorkbenchPartReference partRef) {
		}
	}

	private static class TreeDecoratingLabelProvider extends BaseLabelProvider
			implements ILabelProvider {

		private final DecoratingLabelProvider provider;

		public TreeDecoratingLabelProvider(ILabelProvider provider,
				ILabelDecorator decorator) {
			this.provider = new DecoratingLabelProvider(provider, decorator);
		}

		@Override
		public Image getImage(Object element) {
			return provider.getImage(element);
		}

		@Override
		public String getText(Object element) {
			return provider.getText(element);
		}

		@Override
		public void addListener(ILabelProviderListener listener) {
			provider.addListener(listener);
		}

		@Override
		public void removeListener(ILabelProviderListener listener) {
			provider.removeListener(listener);
		}

		@Override
		public void dispose() {
			provider.dispose();
		}

		public ILabelProvider getLabelProvider() {
			return provider.getLabelProvider();
		}

	}

	static class StagingViewSearchThread extends Thread {
		private StagingView stagingView;

		private static final Object lock = new Object();

		private volatile static int globalThreadIndex = 0;

		private int currentThreadIx;

		public StagingViewSearchThread(StagingView stagingView) {
			super("staging_view_filter_thread" + ++globalThreadIndex); //$NON-NLS-1$
			this.stagingView = stagingView;
			currentThreadIx = globalThreadIndex;
		}

		@Override
		public void run() {
			synchronized (lock) {
				if (currentThreadIx < globalThreadIndex)
					return;
				stagingView.refreshViewersPreservingExpandedElements();
			}
		}

	}

	private final IPreferenceChangeListener prefListener = new IPreferenceChangeListener() {

		@Override
		public void preferenceChange(PreferenceChangeEvent event) {
			if (!RepositoryUtil.PREFS_DIRECTORIES_REL.equals(event.getKey())) {
				return;
			}

			final Repository repo = currentRepository;
			if (repo == null)
				return;

			if (Activator.getDefault().getRepositoryUtil().contains(repo))
				return;

			reload(null);
		}

	};

	private final IPropertyChangeListener uiPrefsListener = new IPropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent event) {
			if (UIPreferences.COMMIT_DIALOG_WARN_ABOUT_MESSAGE_SECOND_LINE
					.equals(event.getProperty())) {
				asyncExec(new Runnable() {
					@Override
					public void run() {
						if (!commitMessageSection.isDisposed()) {
							updateMessage();
						}
					}
				});
			}
		}
	};

	private Action signedOffByAction;

	private Action addChangeIdAction;

	private Action amendPreviousCommitAction;

	private Action openNewCommitsAction;

	private Action columnLayoutAction;

	private Action fileNameModeAction;

	private Action refreshAction;

	private Action sortAction;

	private SashForm stagingSashForm;

	private IndexDiffChangedListener myIndexDiffListener = new IndexDiffChangedListener() {
		@Override
		public void indexDiffChanged(Repository repository,
				IndexDiffData indexDiffData) {
			reload(repository);
		}
	};

	private IndexDiffCacheEntry cacheEntry;

	private UndoRedoActionGroup undoRedoActionGroup;

	private Button commitButton;

	private Button commitAndPushButton;

	private Section rebaseSection;

	private Button rebaseContinueButton;

	private Button rebaseSkipButton;

	private Button rebaseAbortButton;

	private Button ignoreErrors;

	private ListenerHandle refsChangedListener;

	private LocalResourceManager resources = new LocalResourceManager(
			JFaceResources.getResources());

	private boolean disposed;

	private Image getImage(ImageDescriptor descriptor) {
		return (Image) this.resources.get(descriptor);
	}

	@Override
	public void init(IViewSite site, IMemento viewMemento)
			throws PartInitException {
		super.init(site, viewMemento);
		this.initialSelection = site.getWorkbenchWindow().getSelectionService()
				.getSelection();
	}

	@Override
	public void createPartControl(final Composite parent) {
		GridLayoutFactory.fillDefaults().applyTo(parent);

		toolkit = new FormToolkit(parent.getDisplay());
		parent.addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent e) {
				if (commitMessageComponent.isAmending()
						|| userEnteredCommitMessage())
					saveCommitMessageComponentState();
				else
					deleteCommitMessageComponentState();
				resources.dispose();
				toolkit.dispose();
			}
		});

		form = toolkit.createForm(parent);
		parent.addControlListener(new ControlListener() {

			private int[] defaultWeights = { 1, 1 };

			@Override
			public void controlResized(ControlEvent e) {
				org.eclipse.swt.graphics.Rectangle b = parent.getBounds();
				int oldOrientation = mainSashForm.getOrientation();
				if ((oldOrientation == SWT.HORIZONTAL)
						&& (b.height > b.width)) {
					mainSashForm.setOrientation(SWT.VERTICAL);
					mainSashForm.setWeights(defaultWeights);
				} else if ((oldOrientation == SWT.VERTICAL)
						&& (b.height <= b.width)) {
					mainSashForm.setOrientation(SWT.HORIZONTAL);
					mainSashForm.setWeights(defaultWeights);
				}
			}

			@Override
			public void controlMoved(ControlEvent e) {
			}
		});
		form.setImage(getImage(UIIcons.REPOSITORY));
		form.setText(UIText.StagingView_NoSelectionTitle);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(form);
		toolkit.decorateFormHeading(form);
		GridLayoutFactory.swtDefaults().applyTo(form.getBody());

		mainSashForm = new SashForm(form.getBody(), SWT.HORIZONTAL);
		saveSashFormWeightsOnDisposal(mainSashForm,
				HORIZONTAL_SASH_FORM_WEIGHT);
		toolkit.adapt(mainSashForm, true, true);
		GridDataFactory.fillDefaults().grab(true, true)
				.applyTo(mainSashForm);

		stagingSashForm = new SashForm(mainSashForm,
				getStagingFormOrientation());
		saveSashFormWeightsOnDisposal(stagingSashForm,
				STAGING_SASH_FORM_WEIGHT);
		toolkit.adapt(stagingSashForm, true, true);
		GridDataFactory.fillDefaults().grab(true, true)
				.applyTo(stagingSashForm);

		unstagedSection = toolkit.createSection(stagingSashForm,
				ExpandableComposite.TITLE_BAR);

		unstagedSection.setLayoutData(
				GridDataFactory.fillDefaults().grab(true, true).create());

		createUnstagedToolBarComposite();

		Composite unstagedComposite = toolkit.createComposite(unstagedSection);
		toolkit.paintBordersFor(unstagedComposite);
		unstagedSection.setClient(unstagedComposite);
		GridLayoutFactory.fillDefaults().extendedMargins(2, 2, 2, 2)
				.applyTo(unstagedComposite);

		unstagedViewer = createViewer(unstagedComposite, true,
				selection -> unstage(selection));
		Composite rebaseAndCommitComposite = toolkit.createComposite(mainSashForm);
		rebaseAndCommitComposite.setLayout(GridLayoutFactory.fillDefaults().create());

		rebaseSection = toolkit.createSection(rebaseAndCommitComposite,
				ExpandableComposite.TITLE_BAR);
		rebaseSection.setText(UIText.StagingView_RebaseLabel);

		Composite rebaseComposite = toolkit.createComposite(rebaseSection);
		toolkit.paintBordersFor(rebaseComposite);
		rebaseSection.setClient(rebaseComposite);

		rebaseSection.setLayoutData(GridDataFactory.fillDefaults().create());
		rebaseComposite.setLayout(GridLayoutFactory.fillDefaults()
				.numColumns(3).equalWidth(true).create());
		GridDataFactory buttonGridData = GridDataFactory.fillDefaults().align(
				SWT.FILL, SWT.CENTER);

		this.rebaseAbortButton = toolkit.createButton(rebaseComposite,
				UIText.StagingView_RebaseAbort, SWT.PUSH);
		rebaseAbortButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				rebaseAbort();
			}
		});
		rebaseAbortButton.setImage(getImage(UIIcons.REBASE_ABORT));
		buttonGridData.applyTo(rebaseAbortButton);

		this.rebaseSkipButton = toolkit.createButton(rebaseComposite,
				UIText.StagingView_RebaseSkip, SWT.PUSH);
		rebaseSkipButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				rebaseSkip();
			}
		});
		rebaseSkipButton.setImage(getImage(UIIcons.REBASE_SKIP));
		buttonGridData.applyTo(rebaseSkipButton);

		this.rebaseContinueButton = toolkit.createButton(rebaseComposite,
				UIText.StagingView_RebaseContinue, SWT.PUSH);
		rebaseContinueButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				rebaseContinue();
			}
		});
		rebaseContinueButton.setImage(getImage(UIIcons.REBASE_CONTINUE));
		buttonGridData.applyTo(rebaseContinueButton);

		showControl(rebaseSection, false);

		commitMessageSection = toolkit.createSection(rebaseAndCommitComposite,
				ExpandableComposite.TITLE_BAR);
		commitMessageSection.setText(UIText.StagingView_CommitMessage);
		commitMessageSection.setLayoutData(GridDataFactory.fillDefaults()
				.grab(true, true).create());

		Composite commitMessageToolbarComposite = toolkit
				.createComposite(commitMessageSection);
		commitMessageToolbarComposite.setBackground(null);
		commitMessageToolbarComposite.setLayout(createRowLayoutWithoutMargin());
		commitMessageSection.setTextClient(commitMessageToolbarComposite);
		ToolBarManager commitMessageToolBarManager = new ToolBarManager(
				SWT.FLAT | SWT.HORIZONTAL);

		amendPreviousCommitAction = new Action(
				UIText.StagingView_Ammend_Previous_Commit, IAction.AS_CHECK_BOX) {

			@Override
			public void run() {
				commitMessageComponent.setAmendingButtonSelection(isChecked());
				updateMessage();
			}
		};
		amendPreviousCommitAction.setImageDescriptor(UIIcons.AMEND_COMMIT);
		commitMessageToolBarManager.add(amendPreviousCommitAction);

		signedOffByAction = new Action(UIText.StagingView_Add_Signed_Off_By,
				IAction.AS_CHECK_BOX) {

			@Override
			public void run() {
				commitMessageComponent.setSignedOffButtonSelection(isChecked());
			}
		};
		signedOffByAction.setImageDescriptor(UIIcons.SIGNED_OFF);
		commitMessageToolBarManager.add(signedOffByAction);

		addChangeIdAction = new Action(UIText.StagingView_Add_Change_ID,
				IAction.AS_CHECK_BOX) {

			@Override
			public void run() {
				commitMessageComponent.setChangeIdButtonSelection(isChecked());
			}
		};
		addChangeIdAction.setImageDescriptor(UIIcons.GERRIT);
		commitMessageToolBarManager.add(addChangeIdAction);

		commitMessageToolBarManager
				.createControl(commitMessageToolbarComposite);

		Composite commitMessageComposite = toolkit
				.createComposite(commitMessageSection);
		commitMessageSection.setClient(commitMessageComposite);
		GridLayoutFactory.fillDefaults().numColumns(1)
				.applyTo(commitMessageComposite);

		warningLabel = new ToggleableWarningLabel(commitMessageComposite,
				SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, false).exclude(true)
				.applyTo(warningLabel);

		Composite commitMessageTextComposite = toolkit
				.createComposite(commitMessageComposite);
		toolkit.paintBordersFor(commitMessageTextComposite);
		GridDataFactory.fillDefaults().grab(true, true)
				.applyTo(commitMessageTextComposite);
		GridLayoutFactory.fillDefaults().numColumns(1)
				.extendedMargins(2, 2, 2, 2)
				.applyTo(commitMessageTextComposite);

		final CommitProposalProcessor commitProposalProcessor = new CommitProposalProcessor() {
			@Override
			protected Collection<String> computeFileNameProposals() {
				return getStagedFileNames();
			}

			@Override
			protected Collection<String> computeMessageProposals() {
				return CommitMessageHistory.getCommitHistory();
			}
		};
		commitMessageText = new CommitMessageArea(commitMessageTextComposite,
				EMPTY_STRING, toolkit.getBorderStyle()) {
			@Override
			protected CommitProposalProcessor getCommitProposalProcessor() {
				return commitProposalProcessor;
			}
			@Override
			protected IHandlerService getHandlerService() {
				return CommonUtils.getService(getSite(), IHandlerService.class);
			}
		};
		commitMessageText.setData(FormToolkit.KEY_DRAW_BORDER,
				FormToolkit.TEXT_BORDER);
		GridDataFactory.fillDefaults().grab(true, true)
				.applyTo(commitMessageText);
		UIUtils.addBulbDecorator(commitMessageText.getTextWidget(),
				UIText.CommitDialog_ContentAssist);

		Composite composite = toolkit.createComposite(commitMessageComposite);
		toolkit.paintBordersFor(composite);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(composite);
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(composite);

		toolkit.createLabel(composite, UIText.StagingView_Author)
				.setForeground(
						toolkit.getColors().getColor(IFormColors.TB_TOGGLE));
		authorText = toolkit.createText(composite, null);
		authorText
				.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		authorText.setLayoutData(GridDataFactory.fillDefaults()
				.grab(true, false).create());

		toolkit.createLabel(composite, UIText.StagingView_Committer)
				.setForeground(
						toolkit.getColors().getColor(IFormColors.TB_TOGGLE));
		committerText = toolkit.createText(composite, null);
		committerText.setData(FormToolkit.KEY_DRAW_BORDER,
				FormToolkit.TEXT_BORDER);
		committerText.setLayoutData(GridDataFactory.fillDefaults()
				.grab(true, false).create());

		Composite buttonsContainer = toolkit.createComposite(composite);
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1)
				.indent(0, 8).applyTo(buttonsContainer);
		GridLayoutFactory.fillDefaults().numColumns(2)
				.applyTo(buttonsContainer);

		ignoreErrors = toolkit.createButton(buttonsContainer,
					UIText.StagingView_IgnoreErrors, SWT.CHECK);
		ignoreErrors.setSelection(false);
		ignoreErrors.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateMessage();
				updateCommitButtons();
			}
		});
		getPreferenceStore()
				.addPropertyChangeListener(new IPropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent event) {
						if (isDisposed()) {
							getPreferenceStore()
									.removePropertyChangeListener(this);
							return;
						}
						asyncExec(new Runnable() {
							@Override
							public void run() {
								updateIgnoreErrorsButtonVisibility();
								updateMessage();
								updateCommitButtons();
							}
						});
					}
				});

		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
				.grab(true, true).applyTo(ignoreErrors);
		updateIgnoreErrorsButtonVisibility();

		Label filler = toolkit.createLabel(buttonsContainer, ""); //$NON-NLS-1$
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL)
				.grab(true, true).applyTo(filler);

		Composite commitButtonsContainer = toolkit
				.createComposite(buttonsContainer);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER)
				.applyTo(commitButtonsContainer);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(true)
				.applyTo(commitButtonsContainer);


		this.commitAndPushButton = toolkit.createButton(commitButtonsContainer,
				UIText.StagingView_CommitAndPush, SWT.PUSH);
		commitAndPushButton.setImage(getImage(UIIcons.PUSH));
		commitAndPushButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				commit(true);
			}
		});
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER)
				.applyTo(commitAndPushButton);

		this.commitButton = toolkit.createButton(commitButtonsContainer,
				UIText.StagingView_Commit, SWT.PUSH);
		commitButton.setImage(getImage(UIIcons.COMMIT));
		commitButton.setText(UIText.StagingView_Commit);
		commitButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				commit(false);
			}
		});
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER)
				.applyTo(commitButton);

		stagedSection = toolkit.createSection(stagingSashForm,
				ExpandableComposite.TITLE_BAR);

		createStagedToolBarComposite();

		Composite stagedComposite = toolkit.createComposite(stagedSection);
		toolkit.paintBordersFor(stagedComposite);
		stagedSection.setClient(stagedComposite);
		GridLayoutFactory.fillDefaults().extendedMargins(2, 2, 2, 2)
				.applyTo(stagedComposite);

		stagedViewer = createViewer(stagedComposite, false,
				selection -> stage(selection));
		stagedViewer.getLabelProvider().addListener(event -> {
			updateMessage();
			updateCommitButtons();
		});

		selectionChangedListener = new ISelectionListener() {
			@Override
			public void selectionChanged(IWorkbenchPart part,
					ISelection selection) {
				if (part == getSite().getPart()) {
					return;
				}
				if (selection instanceof StructuredSelection) {
					reactOnSelection((StructuredSelection) selection);
				}
			}
		};

		partListener = new PartListener();

		IPreferenceStore preferenceStore = getPreferenceStore();
		if (preferenceStore.contains(UIPreferences.STAGING_VIEW_SYNC_SELECTION))
			reactOnSelection = preferenceStore.getBoolean(
					UIPreferences.STAGING_VIEW_SYNC_SELECTION);
		else
			preferenceStore.setDefault(UIPreferences.STAGING_VIEW_SYNC_SELECTION, true);

		preferenceStore.addPropertyChangeListener(uiPrefsListener);

		InstanceScope.INSTANCE.getNode(
				org.eclipse.egit.core.Activator.getPluginId())
				.addPreferenceChangeListener(prefListener);

		updateSectionText();
		updateToolbar();
		enableCommitWidgets(false);
		refreshAction.setEnabled(false);

		createPopupMenu(unstagedViewer);
		createPopupMenu(stagedViewer);

		final ICommitMessageComponentNotifications listener = new ICommitMessageComponentNotifications() {

			@Override
			public void updateSignedOffToggleSelection(boolean selection) {
				signedOffByAction.setChecked(selection);
			}

			@Override
			public void updateChangeIdToggleSelection(boolean selection) {
				addChangeIdAction.setChecked(selection);
				commitAndPushButton
						.setImage(getImage(
								selection ? UIIcons.GERRIT : UIIcons.PUSH));
			}

			@Override
			public void statusUpdated() {
				updateMessage();
			}
		};
		commitMessageComponent = new CommitMessageComponent(listener);
		commitMessageComponent.attachControls(commitMessageText, authorText,
				committerText);

		commitMessageText.getTextWidget().addVerifyKeyListener(new VerifyKeyListener() {
			@Override
			public void verifyKey(VerifyEvent event) {
				if (UIUtils.isSubmitKeyEvent(event)) {
					event.doit = false;
					commit(false);
				}
			}
		});

		commitMessageText.getTextWidget().addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				String commitButtonTooltip = MessageFormat.format(
						UIText.StagingView_CommitToolTip,
						UIUtils.SUBMIT_KEY_STROKE.format());
				commitButton.setToolTipText(commitButtonTooltip);
			}

			@Override
			public void focusLost(FocusEvent e) {
				commitButton.setToolTipText(null);
			}
		});

		IWorkbenchPartSite site = getSite();
		ISelectionService srv = CommonUtils.getService(site, ISelectionService.class);
		srv.addPostSelectionListener(selectionChangedListener);
		CommonUtils.getService(site, IPartService.class).addPartListener(
				partListener);

		UIUtils.notifySelectionChangedWithCurrentSelection(
				selectionChangedListener, site);

		site.setSelectionProvider(unstagedViewer);

		ViewerFilter filter = new ViewerFilter() {
			@Override
			public boolean select(Viewer viewer, Object parentElement,
					Object element) {
				StagingViewContentProvider contentProvider = getContentProvider((TreeViewer) viewer);
				if (element instanceof StagingEntry)
					return contentProvider.isInFilter((StagingEntry) element);
				else if (element instanceof StagingFolderEntry)
					return contentProvider
							.hasVisibleChildren((StagingFolderEntry) element);
				return true;
			}
		};
		unstagedViewer.addFilter(filter);
		stagedViewer.addFilter(filter);

		restoreSashFormWeights();
		reactOnInitialSelection();

		IWorkbenchSiteProgressService service = CommonUtils.getService(
				getSite(), IWorkbenchSiteProgressService.class);
		if (service != null && reactOnSelection)
			service.showBusyForFamily(org.eclipse.egit.core.JobFamilies.INDEX_DIFF_CACHE_UPDATE);
	}

	private boolean commitAndPushEnabled(boolean commitEnabled) {
		Repository repo = currentRepository;
		if (repo == null) {
			return false;
		}
		return commitEnabled && !repo.getRepositoryState().isRebasing();
	}

	private void updateIgnoreErrorsButtonVisibility() {
		boolean visible = getPreferenceStore()
				.getBoolean(UIPreferences.WARN_BEFORE_COMMITTING)
				&& getPreferenceStore().getBoolean(UIPreferences.BLOCK_COMMIT);
		showControl(ignoreErrors, visible);
		mainSashForm.layout();
	}

	private int getProblemsSeverity() {
		int result = IProblemDecoratable.SEVERITY_NONE;
		StagingViewContentProvider stagedContentProvider = getContentProvider(
				stagedViewer);
		StagingEntry[] entries = stagedContentProvider.getStagingEntries();
		for (StagingEntry entry : entries) {
			if (entry.getProblemSeverity() >= IMarker.SEVERITY_WARNING) {
				if (result < entry.getProblemSeverity()) {
					result = entry.getProblemSeverity();
				}
			}
		}
		return result;
	}

	private void updateCommitButtons() {
		IndexDiffData indexDiff;
		if (cacheEntry != null) {
			indexDiff = cacheEntry.getIndexDiff();
		} else {
			Repository repo = currentRepository;
			if (repo == null) {
				indexDiff = null;
			} else {
				indexDiff = doReload(repo);
			}
		}
		boolean indexDiffAvailable = indexDiffAvailable(indexDiff);
		boolean noConflicts = noConflicts(indexDiff);

		boolean commitEnabled = !isCommitBlocked() && noConflicts
				&& indexDiffAvailable;

		boolean commitAndPushEnabled = commitAndPushEnabled(commitEnabled);

		commitButton.setEnabled(commitEnabled);
		commitAndPushButton.setEnabled(commitAndPushEnabled);
	}

	private void saveSashFormWeightsOnDisposal(final SashForm sashForm,
			final String settingsKey) {
		sashForm.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				getDialogSettings().put(settingsKey,
						intArrayToString(sashForm.getWeights()));
			}
		});
	}

	private IDialogSettings getDialogSettings() {
		return DialogSettings.getOrCreateSection(
				Activator.getDefault().getDialogSettings(),
				StagingView.class.getName());
	}

	private static String intArrayToString(int[] ints) {
		StringBuilder res = new StringBuilder();
		if (ints != null && ints.length > 0) {
			res.append(String.valueOf(ints[0]));
			for (int i = 1; i < ints.length; i++) {
				res.append(',');
				res.append(String.valueOf(ints[i]));
			}
		}
		return res.toString();
	}

	private void restoreSashFormWeights() {
		restoreSashFormWeights(mainSashForm,
				HORIZONTAL_SASH_FORM_WEIGHT);
		restoreSashFormWeights(stagingSashForm,
				STAGING_SASH_FORM_WEIGHT);
	}

	private void restoreSashFormWeights(SashForm sashForm, String settingsKey) {
		IDialogSettings settings = getDialogSettings();
		String weights = settings.get(settingsKey);
		if (weights != null && !weights.isEmpty()) {
			sashForm.setWeights(stringToIntArray(weights));
		}
	}

	private static int[] stringToIntArray(String s) {
		String[] parts = s.split(","); //$NON-NLS-1$
		int[] ints = new int[parts.length];
		for (int i = 0; i < parts.length; i++) {
			ints[i] = Integer.parseInt(parts[i]);
		}
		return ints;
	}

	private void reactOnInitialSelection() {
		StructuredSelection sel = null;
		if (initialSelection instanceof StructuredSelection) {
			sel = (StructuredSelection) initialSelection;
		} else if (initialSelection != null && !initialSelection.isEmpty()) {
			sel = getSelectionOfActiveEditor();
		}
		if (sel != null) {
			reactOnSelection(sel);
		}
		initialSelection = null;
	}

	private StructuredSelection getSelectionOfActiveEditor() {
		IEditorPart activeEditor = getSite().getPage().getActiveEditor();
		if (activeEditor == null) {
			return null;
		}
		return getSelectionOfPart(activeEditor);
	}

	private static StructuredSelection getSelectionOfPart(IWorkbenchPart part) {
		StructuredSelection sel = null;
		if (part instanceof IEditorPart) {
			IResource resource = getResource((IEditorPart) part);
			if (resource != null) {
				sel = new StructuredSelection(resource);
			} else {
				Repository repository = getRepository((IEditorPart) part);
				if (repository != null) {
					sel = new StructuredSelection(repository);
				}
			}
		} else {
			ISelection selection = part.getSite().getPage().getSelection();
			if (selection instanceof StructuredSelection) {
				sel = (StructuredSelection) selection;
			}
		}
		return sel;
	}

	@Nullable
	private static Repository getRepository(IEditorPart part) {
		IEditorInput input = part.getEditorInput();
		if (!(input instanceof IURIEditorInput)) {
			return null;
		}
		return AdapterUtils.adapt(input, Repository.class);
	}

	private static IResource getResource(IEditorPart part) {
		IEditorInput input = part.getEditorInput();
		if (input instanceof IFileEditorInput) {
			return ((IFileEditorInput) input).getFile();
		} else {
			return AdapterUtils.adaptToAnyResource(input);
		}
	}

	private boolean getSortCheckState() {
		return getDialogSettings().getBoolean(STORE_SORT_STATE);
	}

	private void executeRebaseOperation(AbstractRebaseCommandHandler command) {
		try {
			command.execute(currentRepository);
		} catch (ExecutionException e) {
			Activator.showError(e.getMessage(), e);
		}
	}

	protected void rebaseAbort() {
		AbortRebaseCommand abortCommand = new AbortRebaseCommand();
		executeRebaseOperation(abortCommand);
	}

	protected void rebaseSkip() {
		SkipRebaseCommand skipCommand = new SkipRebaseCommand();
		executeRebaseOperation(skipCommand);
	}

	protected void rebaseContinue() {
		ContinueRebaseCommand continueCommand = new ContinueRebaseCommand();
		executeRebaseOperation(continueCommand);
	}

	private void createUnstagedToolBarComposite() {
		Composite unstagedToolbarComposite = toolkit
				.createComposite(unstagedSection);
		unstagedToolbarComposite.setBackground(null);
		unstagedToolbarComposite.setLayout(createRowLayoutWithoutMargin());
		unstagedSection.setTextClient(unstagedToolbarComposite);
		unstagedExpandAllAction = new Action(UIText.UIUtils_ExpandAll,
				IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				unstagedViewer.expandAll();
				enableAutoExpand(unstagedViewer);
			}
		};
		unstagedExpandAllAction.setImageDescriptor(UIIcons.EXPAND_ALL);
		unstagedExpandAllAction.setId(EXPAND_ALL_ITEM_TOOLBAR_ID);

		unstagedCollapseAllAction = new Action(UIText.UIUtils_CollapseAll,
				IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				unstagedViewer.collapseAll();
				disableAutoExpand(unstagedViewer);
			}
		};
		unstagedCollapseAllAction.setImageDescriptor(UIIcons.COLLAPSEALL);
		unstagedCollapseAllAction.setId(COLLAPSE_ALL_ITEM_TOOLBAR_ID);

		sortAction = new Action(UIText.StagingView_UnstagedSort,
				IAction.AS_CHECK_BOX) {

			@Override
			public void run() {
				StagingEntryComparator comparator = (StagingEntryComparator) unstagedViewer
						.getComparator();
				comparator.setAlphabeticSort(!isChecked());
				comparator = (StagingEntryComparator) stagedViewer.getComparator();
				comparator.setAlphabeticSort(!isChecked());
				unstagedViewer.refresh();
				stagedViewer.refresh();
			}
		};

		sortAction.setImageDescriptor(UIIcons.STATE_SORT);
		sortAction.setId(SORT_ITEM_TOOLBAR_ID);
		sortAction.setChecked(getSortCheckState());

		unstagedToolBarManager = new ToolBarManager(SWT.FLAT | SWT.HORIZONTAL);

		unstagedToolBarManager.add(sortAction);
		unstagedToolBarManager.add(unstagedExpandAllAction);
		unstagedToolBarManager.add(unstagedCollapseAllAction);

		unstagedToolBarManager.update(true);
		unstagedToolBarManager.createControl(unstagedToolbarComposite);
	}

	private void createStagedToolBarComposite() {
		Composite stagedToolbarComposite = toolkit
				.createComposite(stagedSection);
		stagedToolbarComposite.setBackground(null);
		stagedToolbarComposite.setLayout(createRowLayoutWithoutMargin());
		stagedSection.setTextClient(stagedToolbarComposite);
		stagedExpandAllAction = new Action(UIText.UIUtils_ExpandAll,
				IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				stagedViewer.expandAll();
				enableAutoExpand(stagedViewer);
			}
		};
		stagedExpandAllAction.setImageDescriptor(UIIcons.EXPAND_ALL);
		stagedExpandAllAction.setId(EXPAND_ALL_ITEM_TOOLBAR_ID);

		stagedCollapseAllAction = new Action(UIText.UIUtils_CollapseAll,
				IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				stagedViewer.collapseAll();
				disableAutoExpand(stagedViewer);
			}
		};
		stagedCollapseAllAction.setImageDescriptor(UIIcons.COLLAPSEALL);
		stagedCollapseAllAction.setId(COLLAPSE_ALL_ITEM_TOOLBAR_ID);

		stagedToolBarManager = new ToolBarManager(SWT.FLAT | SWT.HORIZONTAL);

		stagedToolBarManager.add(stagedExpandAllAction);
		stagedToolBarManager.add(stagedCollapseAllAction);
		stagedToolBarManager.update(true);
		stagedToolBarManager.createControl(stagedToolbarComposite);
	}

	private static RowLayout createRowLayoutWithoutMargin() {
		RowLayout layout = new RowLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.marginTop = 0;
		layout.marginBottom = 0;
		layout.marginLeft = 0;
		layout.marginRight = 0;
		return layout;
	}

	private static void addListenerToDisableAutoExpandOnCollapse(
			TreeViewer treeViewer) {
		treeViewer.addTreeListener(new ITreeViewerListener() {
			@Override
			public void treeCollapsed(TreeExpansionEvent event) {
				disableAutoExpand(event.getTreeViewer());
			}

			@Override
			public void treeExpanded(TreeExpansionEvent event) {
			}
		});
	}

	private static void enableAutoExpand(AbstractTreeViewer treeViewer) {
		treeViewer.setAutoExpandLevel(AbstractTreeViewer.ALL_LEVELS);
	}

	private static void disableAutoExpand(AbstractTreeViewer treeViewer) {
		treeViewer.setAutoExpandLevel(0);
	}

	public Repository getCurrentRepository() {
		return currentRepository;
	}

	@Override
	public ShowInContext getShowInContext() {
		if (stagedViewer != null && stagedViewer.getTree().isFocusControl())
			return getShowInContext(stagedViewer);
		else if (unstagedViewer != null
				&& unstagedViewer.getTree().isFocusControl())
			return getShowInContext(unstagedViewer);
		else
			return null;
	}

	private ShowInContext getShowInContext(TreeViewer treeViewer) {
		IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
		List<Object> elements = new ArrayList<>();
		for (Object selectedElement : selection.toList()) {
			if (selectedElement instanceof StagingEntry) {
				StagingEntry entry = (StagingEntry) selectedElement;
				IFile file = entry.getFile();
				if (file != null)
					elements.add(file);
				else
					elements.add(entry.getLocation());
			} else if (selectedElement instanceof StagingFolderEntry) {
				StagingFolderEntry entry = (StagingFolderEntry) selectedElement;
				IContainer container = entry.getContainer();
				if (container != null)
					elements.add(container);
				else
					elements.add(entry.getLocation());
			}
		}
		return new ShowInContext(null, new StructuredSelection(elements));
	}

	private int getStagingFormOrientation() {
		boolean columnLayout = Activator.getDefault().getPreferenceStore()
				.getBoolean(UIPreferences.STAGING_VIEW_COLUMN_LAYOUT);
		if (columnLayout)
			return SWT.HORIZONTAL;
		else
			return SWT.VERTICAL;
	}

	private void enableAllWidgets(boolean enabled) {
		if (isDisposed())
			return;
		enableCommitWidgets(enabled);
		commitMessageText.setEnabled(enabled);
		enableStagingWidgets(enabled);
	}

	private void enableStagingWidgets(boolean enabled) {
		if (isDisposed())
			return;
		unstagedViewer.getControl().setEnabled(enabled);
		stagedViewer.getControl().setEnabled(enabled);
	}

	private void enableCommitWidgets(boolean enabled) {
		if (isDisposed()) {
			return;
		}
		committerText.setEnabled(enabled);
		enableAuthorText(enabled);
		amendPreviousCommitAction.setEnabled(enabled);
		signedOffByAction.setEnabled(enabled);
		addChangeIdAction.setEnabled(enabled);
		commitButton.setEnabled(enabled);
		commitAndPushButton.setEnabled(enabled);
	}

	private void enableAuthorText(boolean enabled) {
		Repository repo = currentRepository;
		if (repo != null && repo.getRepositoryState()
				.equals(RepositoryState.CHERRY_PICKING_RESOLVED)) {
			authorText.setEnabled(false);
		} else {
			authorText.setEnabled(enabled);
		}
	}

	private void updateToolbar() {

		ControlContribution controlContribution = new ControlContribution(
				"StagingView.searchText") { //$NON-NLS-1$
			@Override
			protected Control createControl(Composite parent) {
				Composite toolbarComposite = toolkit.createComposite(parent,
						SWT.NONE);
				toolbarComposite.setBackground(null);
				GridLayout headLayout = new GridLayout();
				headLayout.numColumns = 2;
				headLayout.marginHeight = 0;
				headLayout.marginWidth = 0;
				headLayout.marginTop = 0;
				headLayout.marginBottom = 0;
				headLayout.marginLeft = 0;
				headLayout.marginRight = 0;
				toolbarComposite.setLayout(headLayout);

				filterText = new Text(toolbarComposite, SWT.SEARCH
						| SWT.ICON_CANCEL | SWT.ICON_SEARCH);
				filterText.setMessage(UIText.StagingView_Find);
				GridData data = new GridData(GridData.FILL_HORIZONTAL);
				data.widthHint = 150;
				filterText.setLayoutData(data);
				final Display display = Display.getCurrent();
				filterText.addModifyListener(new ModifyListener() {
					@Override
					public void modifyText(ModifyEvent e) {
						final StagingViewSearchThread searchThread = new StagingViewSearchThread(
								StagingView.this);
						display.timerExec(200, new Runnable() {
							@Override
							public void run() {
								searchThread.start();
							}
						});
					}
				});
				return toolbarComposite;
			}
		};

		IActionBars actionBars = getViewSite().getActionBars();
		IToolBarManager toolbar = actionBars.getToolBarManager();

		toolbar.add(controlContribution);

		refreshAction = new Action(UIText.StagingView_Refresh, IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				if (cacheEntry != null) {
					schedule(
							cacheEntry.createRefreshResourcesAndIndexDiffJob(),
							false);
				}
			}
		};
		refreshAction.setImageDescriptor(UIIcons.ELCL16_REFRESH);
		toolbar.add(refreshAction);

		Action linkSelectionAction = new BooleanPrefAction(
				(IPersistentPreferenceStore) getPreferenceStore(),
				UIPreferences.STAGING_VIEW_SYNC_SELECTION,
				UIText.StagingView_LinkSelection) {
			@Override
			public void apply(boolean value) {
				reactOnSelection = value;
			}
		};
		linkSelectionAction.setImageDescriptor(UIIcons.ELCL16_SYNCED);
		toolbar.add(linkSelectionAction);

		toolbar.add(new Separator());

		compareModeAction = new Action(UIText.StagingView_CompareMode,
				IAction.AS_CHECK_BOX) {
			@Override
			public void run() {
				getPreferenceStore().setValue(
						UIPreferences.STAGING_VIEW_COMPARE_MODE, isChecked());
			}
		};
		compareModeAction.setImageDescriptor(UIIcons.ELCL16_COMPARE_VIEW);
		compareModeAction.setChecked(getPreferenceStore()
				.getBoolean(UIPreferences.STAGING_VIEW_COMPARE_MODE));

		toolbar.add(compareModeAction);
		toolbar.add(new Separator());

		openNewCommitsAction = new Action(UIText.StagingView_OpenNewCommits,
				IAction.AS_CHECK_BOX) {

			@Override
			public void run() {
				getPreferenceStore().setValue(
						UIPreferences.STAGING_VIEW_SHOW_NEW_COMMITS, isChecked());
			}
		};
		openNewCommitsAction.setChecked(getPreferenceStore().getBoolean(
				UIPreferences.STAGING_VIEW_SHOW_NEW_COMMITS));

		columnLayoutAction = new Action(UIText.StagingView_ColumnLayout,
				IAction.AS_CHECK_BOX) {

			@Override
			public void run() {
				getPreferenceStore().setValue(
						UIPreferences.STAGING_VIEW_COLUMN_LAYOUT, isChecked());
				stagingSashForm.setOrientation(isChecked() ? SWT.HORIZONTAL
						: SWT.VERTICAL);
			}
		};
		columnLayoutAction.setChecked(getPreferenceStore().getBoolean(
				UIPreferences.STAGING_VIEW_COLUMN_LAYOUT));

		fileNameModeAction = new Action(UIText.StagingView_ShowFileNamesFirst,
				IAction.AS_CHECK_BOX) {

			@Override
			public void run() {
				final boolean enable = isChecked();
				getLabelProvider(stagedViewer).setFileNameMode(enable);
				getLabelProvider(unstagedViewer).setFileNameMode(enable);
				getContentProvider(stagedViewer).setFileNameMode(enable);
				getContentProvider(unstagedViewer).setFileNameMode(enable);
				StagingEntryComparator comparator = (StagingEntryComparator) unstagedViewer
						.getComparator();
				comparator.setFileNamesFirst(enable);
				comparator = (StagingEntryComparator) stagedViewer.getComparator();
				comparator.setFileNamesFirst(enable);
				getPreferenceStore().setValue(
						UIPreferences.STAGING_VIEW_FILENAME_MODE, enable);
				refreshViewersPreservingExpandedElements();
			}
		};
		fileNameModeAction.setChecked(getPreferenceStore().getBoolean(
				UIPreferences.STAGING_VIEW_FILENAME_MODE));

		IMenuManager dropdownMenu = actionBars.getMenuManager();
		MenuManager presentationMenu = new MenuManager(
				UIText.StagingView_Presentation);
		listPresentationAction = new Action(UIText.StagingView_List,
				IAction.AS_RADIO_BUTTON) {
			@Override
			public void run() {
				if (!isChecked()) {
					return;
				}
				switchToListMode();
				refreshViewers();
			}
		};
		listPresentationAction.setImageDescriptor(UIIcons.FLAT);
		presentationMenu.add(listPresentationAction);

		treePresentationAction = new Action(UIText.StagingView_Tree,
				IAction.AS_RADIO_BUTTON) {
			@Override
			public void run() {
				if (!isChecked()) {
					return;
				}
				presentation = Presentation.TREE;
				setPresentation(presentation, false);
				listPresentationAction.setChecked(false);
				compactTreePresentationAction.setChecked(false);
				setExpandCollapseActionsVisible(false, isExpandAllowed(false),
						true);
				setExpandCollapseActionsVisible(true, isExpandAllowed(true),
						true);
				refreshViewers();
			}
		};
		treePresentationAction.setImageDescriptor(UIIcons.HIERARCHY);
		presentationMenu.add(treePresentationAction);

		compactTreePresentationAction = new Action(UIText.StagingView_CompactTree,
				IAction.AS_RADIO_BUTTON) {
			@Override
			public void run() {
				if (!isChecked()) {
					return;
				}
				switchToCompactModeInternal(false);
				refreshViewers();
			}

		};
		compactTreePresentationAction.setImageDescriptor(UIIcons.COMPACT);
		presentationMenu.add(compactTreePresentationAction);

		presentation = readPresentation(UIPreferences.STAGING_VIEW_PRESENTATION,
				Presentation.LIST);
		switch (presentation) {
		case LIST:
			listPresentationAction.setChecked(true);
			setExpandCollapseActionsVisible(false, false, false);
			setExpandCollapseActionsVisible(true, false, false);
			break;
		case TREE:
			treePresentationAction.setChecked(true);
			break;
		case COMPACT_TREE:
			compactTreePresentationAction.setChecked(true);
			break;
		default:
			break;
		}
		dropdownMenu.add(presentationMenu);
		dropdownMenu.add(new Separator());
		dropdownMenu.add(openNewCommitsAction);
		dropdownMenu.add(columnLayoutAction);
		dropdownMenu.add(fileNameModeAction);
		dropdownMenu.add(compareModeAction);

		actionBars.setGlobalActionHandler(ActionFactory.DELETE.getId(), new GlobalDeleteActionHandler());

		IUndoContext workspaceContext = AdapterUtils.adapt(ResourcesPlugin.getWorkspace(), IUndoContext.class);
		undoRedoActionGroup = new UndoRedoActionGroup(getViewSite(), workspaceContext, true);
		undoRedoActionGroup.fillActionBars(actionBars);

		actionBars.updateActionBars();
	}

	private Presentation readPresentation(String key, Presentation def) {
		String presentationString = getPreferenceStore().getString(key);
		if (presentationString.length() > 0) {
			try {
				return Presentation.valueOf(presentationString);
			} catch (IllegalArgumentException e) {
			}
		}
		return def;
	}

	private void setPresentation(Presentation newOne, boolean auto) {
		Presentation old = presentation;
		presentation = newOne;
		IPreferenceStore store = getPreferenceStore();
		store.setValue(UIPreferences.STAGING_VIEW_PRESENTATION, newOne.name());
		if (auto && old != newOne) {
			store.setValue(UIPreferences.STAGING_VIEW_PRESENTATION_CHANGED,
					true);
		} else {
			store.setToDefault(UIPreferences.STAGING_VIEW_PRESENTATION_CHANGED);
		}
	}

	private void setExpandCollapseActionsVisible(boolean staged,
			boolean visibleExpandAll,
			boolean visibleCollapseAll) {
		ToolBarManager toolBarManager = staged ? stagedToolBarManager
						: unstagedToolBarManager;
		for (IContributionItem item : toolBarManager.getItems()) {
			String id = item.getId();
			if (EXPAND_ALL_ITEM_TOOLBAR_ID.equals(id)) {
				item.setVisible(visibleExpandAll);
			} else if (COLLAPSE_ALL_ITEM_TOOLBAR_ID.equals(id)) {
				item.setVisible(visibleCollapseAll);
			}
		}
		(staged ? stagedExpandAllAction : unstagedExpandAllAction)
				.setEnabled(visibleExpandAll);
		(staged ? stagedCollapseAllAction : unstagedCollapseAllAction)
				.setEnabled(visibleCollapseAll);
		toolBarManager.update(true);
	}

	private boolean isExpandAllowed(boolean staged) {
		StagingViewContentProvider contentProvider = getContentProvider(
				staged ? stagedViewer : unstagedViewer);
		return contentProvider.getCount() <= getMaxLimitForListMode();
	}

	private TreeViewer createTree(Composite composite) {
		Tree tree = toolkit.createTree(composite, SWT.FULL_SELECTION
				| SWT.MULTI);
		TreeViewer treeViewer = new TreeViewer(tree);
		return treeViewer;
	}

	private IBaseLabelProvider createLabelProvider(TreeViewer treeViewer) {
		StagingViewLabelProvider baseProvider = new StagingViewLabelProvider(
				this);
		baseProvider.setFileNameMode(getPreferenceStore().getBoolean(
				UIPreferences.STAGING_VIEW_FILENAME_MODE));

		ProblemLabelDecorator decorator = new ProblemLabelDecorator(treeViewer);
		return new TreeDecoratingLabelProvider(baseProvider, decorator);
	}

	private StagingViewContentProvider createStagingContentProvider(
			boolean unstaged) {
		StagingViewContentProvider provider = new StagingViewContentProvider(
				this, unstaged);
		provider.setFileNameMode(getPreferenceStore().getBoolean(
				UIPreferences.STAGING_VIEW_FILENAME_MODE));
		return provider;
	}

	private TreeViewer createViewer(Composite parent, boolean unstaged,
			final Consumer<IStructuredSelection> dropAction) {
		final TreeViewer viewer = createTree(parent);
		GridDataFactory.fillDefaults().grab(true, true)
				.applyTo(viewer.getControl());
		viewer.getTree().setData(FormToolkit.KEY_DRAW_BORDER,
				FormToolkit.TREE_BORDER);
		viewer.setLabelProvider(createLabelProvider(viewer));
		StagingViewContentProvider contentProvider = createStagingContentProvider(
				unstaged);
		viewer.setContentProvider(contentProvider);
		viewer.addDragSupport(DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK,
				new Transfer[] { LocalSelectionTransfer.getTransfer(),
						FileTransfer.getInstance() },
				new StagingDragListener(viewer, contentProvider, unstaged));
		viewer.addDropSupport(DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK,
				new Transfer[] { LocalSelectionTransfer.getTransfer() },
				new DropTargetAdapter() {

					@Override
					public void drop(DropTargetEvent event) {
						event.detail = DND.DROP_COPY;
						if (event.data instanceof IStructuredSelection) {
							final IStructuredSelection selection = (IStructuredSelection) event.data;
							if ((selection instanceof StagingDragSelection)
									&& ((StagingDragSelection) selection)
											.isFromUnstaged() == unstaged) {
								return;
							}
							dropAction.accept(selection);
						}
					}
				});
		viewer.addOpenListener(event -> compareWith(event));
		viewer.setComparator(new StagingEntryComparator(getSortCheckState(),
				getPreferenceStore()
						.getBoolean(UIPreferences.STAGING_VIEW_FILENAME_MODE)));
		enableAutoExpand(viewer);
		addListenerToDisableAutoExpandOnCollapse(viewer);
		return viewer;
	}

	private void setStagingViewerInput(TreeViewer stagingViewer,
			StagingViewUpdate newInput, Object[] previous,
			Set<IPath> additionalPaths) {
		final Tree tree = stagingViewer.getTree();
		tree.setRedraw(false);
		Cursor oldCursor = tree.getCursor();
		tree.setCursor(tree.getDisplay().getSystemCursor(SWT.CURSOR_WAIT));

		try {
			TreeItem topItem = tree.getTopItem();
			final Set<Object> precedingObjects = new LinkedHashSet<>();
			if (topItem != null) {
				new TreeItemVisitor(tree.getItems()) {
					@Override
					public boolean visit(TreeItem treeItem) {
						precedingObjects.add(treeItem.getData());
						return true;
					}
				}.traverse(topItem);
				precedingObjects.remove(null);
			}

			boolean preserveTop = true;
			boolean keepSelectionVisible = false;
			StagingViewUpdate oldInput = (StagingViewUpdate) stagingViewer
					.getInput();
			if (oldInput != null && oldInput.repository == newInput.repository
					&& oldInput.indexDiff != null) {
				StagingViewContentProvider contentProvider = getContentProvider(
						stagingViewer);
				ViewerComparator comparator = stagingViewer.getComparator();
				Map<String, Object> oldPaths = buildElementMap(stagingViewer,
						contentProvider, comparator);

				stagingViewer.setInput(newInput);
				if (previous != null) {
					expandPreviousExpandedAndPaths(previous, stagingViewer,
							additionalPaths);
				}

				StagingViewerUpdate stagingViewerUpdate = updateSelection(
						stagingViewer, contentProvider, oldPaths,
						buildElementMap(stagingViewer, contentProvider,
								comparator));

				if (stagingViewerUpdate == StagingViewerUpdate.REMOVED) {
					keepSelectionVisible = true;
				} else if (stagingViewerUpdate == StagingViewerUpdate.ADDED) {
					preserveTop = false;
				}
			} else {
				stagingViewer.setInput(newInput);
				if (previous != null) {
					expandPreviousExpandedAndPaths(previous, stagingViewer,
							additionalPaths);
				}
			}

			if (preserveTop) {
				TreeItem[] selection = tree.getSelection();
				TreeItem initialItem = keepSelectionVisible
						&& selection.length > 0 ? selection[0] : null;
				new TreeItemVisitor(tree.getItems()) {
					@Override
					public boolean visit(TreeItem treeItem) {
						if (precedingObjects.contains(treeItem.getData())) {
							tree.setTopItem(treeItem);
							return false;
						}
						return true;
					}
				}.traverse(initialItem);
			}
		} finally {
			tree.setRedraw(true);
			tree.setCursor(oldCursor);
		}
	}

	private static Map<String, Object> buildElementMap(TreeViewer stagingViewer,
			StagingViewContentProvider contentProvider,
			ViewerComparator comparator) {
		Map<String, Object> result = new LinkedHashMap<>();
		Object[] elements = contentProvider.getElements(null);
		comparator.sort(stagingViewer, elements);
		for (Object element : elements) {
			visitElement(stagingViewer, contentProvider, comparator, element,
					result);
		}
		return result;
	}

	private static boolean visitElement(TreeViewer stagingViewer,
			StagingViewContentProvider contentProvider,
			ViewerComparator comparator,
			Object element, Map<String, Object> paths) {
		if (element instanceof StagingEntry) {
			StagingEntry stagingEntry = (StagingEntry) element;
			if (contentProvider.isInFilter(stagingEntry)) {
				String path = stagingEntry.getPath();
				paths.put(path, stagingEntry);
				return true;
			}

			return false;
		}

		if (element instanceof StagingFolderEntry) {
			StagingFolderEntry stagingFolderEntry = (StagingFolderEntry) element;
			Object[] children = contentProvider.getChildren(stagingFolderEntry);
			comparator.sort(stagingViewer, children);

			IPath path = stagingFolderEntry.getPath();
			String pathString = path.toString();
			paths.put(pathString, stagingFolderEntry);

			boolean hasVisibleChildren = false;
			for (Object child : children) {
				if (visitElement(stagingViewer, contentProvider, comparator,
						child, paths)) {
					hasVisibleChildren = true;
				}
			}

			if (hasVisibleChildren) {
				return true;
			}

			paths.remove(pathString);
			return false;
		}

		return false;
	}

	private enum StagingViewerUpdate {
		ADDED, REMOVED, UNCHANGED
	}

	private static StagingViewerUpdate updateSelection(TreeViewer stagingViewer,
			StagingViewContentProvider contentProvider,
			Map<String, Object> oldPaths, Map<String, Object> newPaths) {
		Map<String, Object> addedPaths = new LinkedHashMap<>(newPaths);
		addedPaths.keySet().removeAll(oldPaths.keySet());
		if (!addedPaths.isEmpty()) {
			List<Object> newSelection = new ArrayList<>();
			Set<Object> elements = new LinkedHashSet<>(addedPaths.values());
			Set<Object> excludeChildren = new LinkedHashSet<>();
			for (Object element : elements) {
				if (element instanceof StagingEntry) {
					StagingEntry stagingEntry = (StagingEntry) element;
					if (!excludeChildren.contains(stagingEntry.getParent())) {
						newSelection.add(stagingEntry);
					}
				} else if (element instanceof StagingFolderEntry) {
					StagingFolderEntry stagingFolderEntry = (StagingFolderEntry) element;
					StagingFolderEntry parent = stagingFolderEntry.getParent();
					if (excludeChildren.contains(parent)) {
						excludeChildren.add(stagingFolderEntry);
					} else if (elements.containsAll(contentProvider
							.getStagingEntriesFiltered(stagingFolderEntry))) {
						newSelection.add(stagingFolderEntry);
						excludeChildren.add(stagingFolderEntry);
					}
				}
			}

			stagingViewer.setSelection(new StructuredSelection(newSelection),
					true);
			return StagingViewerUpdate.ADDED;
		} else {
			Map<String, Object> removedPaths = new LinkedHashMap<>(oldPaths);
			removedPaths.keySet().removeAll(newPaths.keySet());
			if (!removedPaths.isEmpty()) {
				Collection<Object> removedElements = removedPaths.values();
				Object firstRemovedElement = removedElements.iterator()
						.next();
				Object parent = contentProvider.getParent(firstRemovedElement);
				Object candidate = null;
				boolean visitSubsequentSiblings = false;
				for (Object oldElement : oldPaths.values()) {
					if (oldElement == firstRemovedElement) {
						visitSubsequentSiblings = true;
					}

					if (visitSubsequentSiblings) {
						if (!removedElements.contains(oldElement)) {
							if (contentProvider
									.getParent(oldElement) == parent) {
								candidate = oldElement;
								break;
							} else if (candidate != null) {
								break;
							}
						}
					} else if (candidate == null || oldElement == parent
							|| contentProvider
									.getParent(oldElement) == parent) {
						candidate = oldElement;
					}
				}

				if (candidate == null && !newPaths.isEmpty()) {
					candidate = newPaths.values().iterator().next();
				}

				if (candidate != null) {
					stagingViewer.setSelection(
							new StructuredSelection(candidate), true);
					return StagingViewerUpdate.REMOVED;
				}
			}

			return StagingViewerUpdate.UNCHANGED;
		}
	}

	private static abstract class TreeItemVisitor {
		private final TreeItem[] roots;

		public TreeItemVisitor(TreeItem[] roots) {
			this.roots = roots;
		}

		public abstract boolean visit(TreeItem treeItem);

		public void traverse(TreeItem treeItem) {
			if (treeItem == null) {
				treeItem = getLastItem(roots);
				if (treeItem == null) {
					return;
				}
			}
			if (treeItem.isDisposed()) {
				return;
			}
			if (treeItem.getData() != null && visit(treeItem)) {
				traversePrecedingSiblings(treeItem);
			}
		}

		private TreeItem getLastItem(TreeItem[] treeItems) {
			if (treeItems.length == 0) {
				return null;
			}
			TreeItem lastItem = treeItems[treeItems.length - 1];
			if (lastItem.getExpanded()) {
				TreeItem result = getLastItem(lastItem.getItems());
				if (result != null) {
					return result;
				}
			}
			return lastItem;
		}

		private boolean traversePrecedingSiblings(TreeItem treeItem) {
			TreeItem parent = treeItem.getParentItem();
			if (parent == null) {
				return traversePrecedingSiblings(roots, treeItem);
			}
			if (!traversePrecedingSiblings(parent.getItems(), treeItem)) {
				return false;
			}
			return traversePrecedingSiblings(parent);
		}

		private boolean traversePrecedingSiblings(TreeItem[] siblings,
				TreeItem treeItem) {
			boolean start = false;
			for (int i = siblings.length - 1; i >= 0; --i) {
				TreeItem sibling = siblings[i];
				if (start) {
					if (!traverseChildren(sibling)) {
						return false;
					}
				} else if (sibling == treeItem) {
					start = true;
				}
			}

			return true;
		}

		private boolean traverseChildren(TreeItem treeItem) {
			if (treeItem.getExpanded()) {
				TreeItem[] children = treeItem.getItems();
				for (int i = children.length - 1; i >= 0; --i) {
					if (!traverseChildren(children[i])) {
						return false;
					}
				}
			}
			return visit(treeItem);
		}
	}

	private IPreferenceStore getPreferenceStore() {
		return Activator.getDefault().getPreferenceStore();
	}

	private StagingViewLabelProvider getLabelProvider(ContentViewer viewer) {
		IBaseLabelProvider base = viewer.getLabelProvider();
		ILabelProvider labelProvider = ((TreeDecoratingLabelProvider) base)
				.getLabelProvider();
		return (StagingViewLabelProvider) labelProvider;
	}

	private StagingViewContentProvider getContentProvider(ContentViewer viewer) {
		return (StagingViewContentProvider) viewer.getContentProvider();
	}

	private void updateSectionText() {
		stagedSection.setText(MessageFormat
				.format(UIText.StagingView_StagedChanges,
						getSectionCount(stagedViewer)));
		unstagedSection.setText(MessageFormat.format(
				UIText.StagingView_UnstagedChanges,
				getSectionCount(unstagedViewer)));
	}

	private String getSectionCount(TreeViewer viewer) {
		StagingViewContentProvider contentProvider = getContentProvider(viewer);
		int count = contentProvider.getCount();
		int shownCount = contentProvider.getShownCount();
		if (shownCount == count)
			return Integer.toString(count);
		else
			return shownCount + "/" + count; //$NON-NLS-1$
	}

	private void updateMessage() {
		if (hasErrorsOrWarnings()) {
			warningLabel.showMessage(UIText.StagingView_MessageErrors);
			commitMessageSection.redraw();
		} else {
			String message = commitMessageComponent.getStatus().getMessage();
			boolean needsRedraw = false;
			if (message != null) {
				warningLabel.showMessage(message);
				needsRedraw = true;
			} else {
				needsRedraw = warningLabel.getVisible();
				warningLabel.hideMessage();
			}
			if (needsRedraw)
				commitMessageSection.redraw();
		}
	}

	private void compareWith(OpenEvent event) {
		IStructuredSelection selection = (IStructuredSelection) event
				.getSelection();
		if (selection.isEmpty()
				|| !(selection.getFirstElement() instanceof StagingEntry))
			return;
		StagingEntry stagingEntry = (StagingEntry) selection.getFirstElement();
		if (stagingEntry.isSubmodule())
			return;
		switch (stagingEntry.getState()) {
		case ADDED:
		case CHANGED:
		case REMOVED:
			runCommand(ActionCommands.COMPARE_INDEX_WITH_HEAD_ACTION, selection);
			break;

		case CONFLICTING:
			runCommand(ActionCommands.MERGE_TOOL_ACTION, selection);
			break;

		case MISSING:
		case MISSING_AND_CHANGED:
		case MODIFIED:
		case MODIFIED_AND_CHANGED:
		case MODIFIED_AND_ADDED:
		case UNTRACKED:
		default:
			if (Activator.getDefault().getPreferenceStore().getBoolean(
					UIPreferences.STAGING_VIEW_COMPARE_MODE)) {
				runCommand(ActionCommands.COMPARE_WITH_INDEX_ACTION, selection);
			} else {
				openSelectionInEditor(selection);
			}

		}
	}

	private void createPopupMenu(final TreeViewer treeViewer) {
		final MenuManager menuMgr = new MenuManager();
		menuMgr.setRemoveAllWhenShown(true);
		Control control = treeViewer.getControl();
		control.setMenu(menuMgr.createContextMenu(control));
		menuMgr.addMenuListener(new IMenuListener() {

			@Override
			public void menuAboutToShow(IMenuManager manager) {
				final IStructuredSelection selection = (IStructuredSelection) treeViewer
						.getSelection();
				if (selection.isEmpty())
					return;

				Set<StagingEntry> stagingEntrySet = new LinkedHashSet<>();
				Set<StagingFolderEntry> stagingFolderSet = new LinkedHashSet<>();

				boolean submoduleSelected = false;
				boolean folderSelected = false;
				boolean onlyFoldersSelected = true;
				for (Object element : selection.toArray()) {
					if (element instanceof StagingFolderEntry) {
						StagingFolderEntry folder = (StagingFolderEntry) element;
						folderSelected = true;
						if (onlyFoldersSelected) {
							stagingFolderSet.add(folder);
						}
						StagingViewContentProvider contentProvider = getContentProvider(treeViewer);
						stagingEntrySet.addAll(contentProvider
								.getStagingEntriesFiltered(folder));
					} else if (element instanceof StagingEntry) {
						if (onlyFoldersSelected) {
							stagingFolderSet.clear();
						}
						onlyFoldersSelected = false;
						StagingEntry entry = (StagingEntry) element;
						if (entry.isSubmodule()) {
							submoduleSelected = true;
						}
						stagingEntrySet.add(entry);
					}
				}

				List<StagingEntry> stagingEntryList = new ArrayList<>(
						stagingEntrySet);
				final IStructuredSelection fileSelection = new StructuredSelection(
						stagingEntryList);
				stagingEntrySet = null;

				if (!folderSelected) {
					Action openWorkingTreeVersion = new Action(
							UIText.CommitFileDiffViewer_OpenWorkingTreeVersionInEditorMenuLabel) {
						@Override
						public void run() {
							openSelectionInEditor(fileSelection);
						}
					};
					openWorkingTreeVersion.setEnabled(!submoduleSelected
							&& anyElementIsExistingFile(fileSelection));
					menuMgr.add(openWorkingTreeVersion);
					String label = stagingEntryList.get(0).isStaged()
									? UIText.CommitFileDiffViewer_CompareWorkingDirectoryMenuLabel
									: UIText.StagingView_CompareWithIndexMenuLabel;
					Action openCompareWithIndex = new Action(label) {
						@Override
						public void run() {
							runCommand(ActionCommands.COMPARE_WITH_INDEX_ACTION,
									fileSelection);
						}
					};
					menuMgr.add(openCompareWithIndex);
				}

				Set<StagingEntry.Action> availableActions = getAvailableActions(fileSelection);

				boolean addReplaceWithFileInGitIndex = availableActions.contains(StagingEntry.Action.REPLACE_WITH_FILE_IN_GIT_INDEX);
				boolean addReplaceWithHeadRevision = availableActions.contains(StagingEntry.Action.REPLACE_WITH_HEAD_REVISION);
				boolean addStage = availableActions.contains(StagingEntry.Action.STAGE);
				boolean addUnstage = availableActions.contains(StagingEntry.Action.UNSTAGE);
				boolean addDelete = availableActions.contains(StagingEntry.Action.DELETE);
				boolean addIgnore = availableActions.contains(StagingEntry.Action.IGNORE);
				boolean addLaunchMergeTool = availableActions.contains(StagingEntry.Action.LAUNCH_MERGE_TOOL);
				boolean addReplaceWithOursTheirsMenu = availableActions
						.contains(StagingEntry.Action.REPLACE_WITH_OURS_THEIRS_MENU);

				if (addStage)
					menuMgr.add(new Action(UIText.StagingView_StageItemMenuLabel) {
						@Override
						public void run() {
							stage(selection);
						}
					});
				if (addUnstage)
					menuMgr.add(new Action(UIText.StagingView_UnstageItemMenuLabel) {
						@Override
						public void run() {
							unstage(selection);
						}
					});
				boolean selectionIncludesNonWorkspaceResources = selectionIncludesNonWorkspaceResources(fileSelection);
				if (addReplaceWithFileInGitIndex)
					if (selectionIncludesNonWorkspaceResources)
						menuMgr.add(new ReplaceAction(
								UIText.StagingView_replaceWithFileInGitIndex,
								fileSelection, false));
					else
						menuMgr.add(createItem(
								UIText.StagingView_replaceWithFileInGitIndex,
								ActionCommands.DISCARD_CHANGES_ACTION,
								fileSelection)); // replace with index
				if (addReplaceWithHeadRevision)
					if (selectionIncludesNonWorkspaceResources)
						menuMgr.add(new ReplaceAction(
								UIText.StagingView_replaceWithHeadRevision,
								fileSelection, true));
					else
						menuMgr.add(createItem(
								UIText.StagingView_replaceWithHeadRevision,
								ActionCommands.REPLACE_WITH_HEAD_ACTION,
								fileSelection));
				if (addIgnore) {
					if (!stagingFolderSet.isEmpty()) {
						menuMgr.add(new IgnoreFoldersAction(stagingFolderSet));
					}
					menuMgr.add(new IgnoreAction(fileSelection));
				}
				if (addDelete)
					menuMgr.add(new DeleteAction(fileSelection));
				if (addLaunchMergeTool)
					menuMgr.add(createItem(UIText.StagingView_MergeTool,
							ActionCommands.MERGE_TOOL_ACTION,
							fileSelection));
				if (addReplaceWithOursTheirsMenu) {
					MenuManager replaceWithMenu = new MenuManager(
							UIText.StagingView_ReplaceWith);
					ReplaceWithOursTheirsMenu oursTheirsMenu = new ReplaceWithOursTheirsMenu();
					oursTheirsMenu.initialize(getSite());
					replaceWithMenu.add(oursTheirsMenu);
					menuMgr.add(replaceWithMenu);
				}
				menuMgr.add(new Separator());
				menuMgr.add(createShowInMenu());
			}
		});

	}

	private boolean anyElementIsExistingFile(IStructuredSelection s) {
		for (Object element : s.toList()) {
			if (element instanceof StagingEntry) {
				StagingEntry entry = (StagingEntry) element;
				if (entry.getType() != IResource.FILE) {
					continue;
				}
				if (entry.getLocation().toFile().exists()) {
					return true;
				}
			}
		}
		return false;
	}

	Presentation getPresentation() {
		return presentation;
	}

	String getFilterString() {
		if (filterText != null && !filterText.isDisposed())
			return filterText.getText().trim();
		else
			return ""; //$NON-NLS-1$
	}

	public void refreshViewers() {
		syncExec(new Runnable() {
			@Override
			public void run() {
				refreshViewersInternal();
			}
		});
	}

	public void refreshViewersPreservingExpandedElements() {
		syncExec(new Runnable() {
			@Override
			public void run() {
				Object[] unstagedExpanded = unstagedViewer.getVisibleExpandedElements();
				Object[] stagedExpanded = stagedViewer.getVisibleExpandedElements();
				refreshViewersInternal();
				unstagedViewer.setExpandedElements(unstagedExpanded);
				stagedViewer.setExpandedElements(stagedExpanded);
			}
		});
	}

	private void refreshViewersInternal() {
		unstagedViewer.refresh();
		stagedViewer.refresh();
		updateSectionText();
	}

	private IContributionItem createShowInMenu() {
		IWorkbenchWindow workbenchWindow = getSite().getWorkbenchWindow();
		return UIUtils.createShowInMenu(workbenchWindow);
	}

	private class ReplaceAction extends Action {

		IStructuredSelection selection;
		private final boolean headRevision;

		ReplaceAction(String text, @NonNull IStructuredSelection selection,
				boolean headRevision) {
			super(text);
			this.selection = selection;
			this.headRevision = headRevision;
		}

		private void getSelectedFiles(@NonNull List<String> files,
				@NonNull List<String> inaccessibleFiles) {
			Iterator iterator = selection.iterator();
			while (iterator.hasNext()) {
				Object selectedItem = iterator.next();
				if (selectedItem instanceof StagingEntry) {
					StagingEntry stagingEntry = (StagingEntry) selectedItem;
					String path = stagingEntry.getPath();
					files.add(path);
					IFile resource = stagingEntry.getFile();
					if (resource == null || !resource.isAccessible()) {
						inaccessibleFiles.add(path);
					}
				}
			}
		}

		private void replaceWith(@NonNull List<String> files,
				@NonNull List<String> inaccessibleFiles) {
			Repository repository = currentRepository;
			if (files.isEmpty() || repository == null) {
				return;
			}
			try (Git git = new Git(repository)) {
				CheckoutCommand checkoutCommand = git.checkout();
				if (headRevision) {
					checkoutCommand.setStartPoint(Constants.HEAD);
				}
				for (String path : files) {
					checkoutCommand.addPath(path);
				}
				checkoutCommand.call();
				if (!inaccessibleFiles.isEmpty()) {
					IndexDiffCacheEntry indexDiffCacheForRepository = org.eclipse.egit.core.Activator
							.getDefault().getIndexDiffCache()
							.getIndexDiffCacheEntry(repository);
					if (indexDiffCacheForRepository != null) {
						indexDiffCacheForRepository
								.refreshFiles(inaccessibleFiles);
					}
				}
			} catch (Exception e) {
				Activator.handleError(UIText.StagingView_checkoutFailed, e,
						true);
			}
		}

		@Override
		public void run() {
			String question = UIText.DiscardChangesAction_confirmActionMessage;
			ILaunchConfiguration launch = LaunchFinder
					.getRunningLaunchConfiguration(
							Collections.singleton(getCurrentRepository()),
							null);
			if (launch != null) {
				question = MessageFormat.format(question,
						"\n\n" + MessageFormat.format( //$NON-NLS-1$
								UIText.LaunchFinder_RunningLaunchMessage,
								launch.getName()));
			} else {
				question = MessageFormat.format(question, ""); //$NON-NLS-1$
			}
			boolean performAction = MessageDialog.openConfirm(form.getShell(),
					UIText.DiscardChangesAction_confirmActionTitle, question);
			if (!performAction) {
				return;
			}
			List<String> files = new ArrayList<>();
			List<String> inaccessibleFiles = new ArrayList<>();
			getSelectedFiles(files, inaccessibleFiles);
			replaceWith(files, inaccessibleFiles);
		}
	}

	private static class IgnoreAction extends Action {

		private final IStructuredSelection selection;

		IgnoreAction(IStructuredSelection selection) {
			super(UIText.StagingView_IgnoreItemMenuLabel);
			this.selection = selection;
		}

		@Override
		public void run() {
			IgnoreOperationUI operation = new IgnoreOperationUI(
					getSelectedPaths(selection));
			operation.run();
		}
	}

	private static class IgnoreFoldersAction extends Action {
		private final Set<StagingFolderEntry> selection;

		IgnoreFoldersAction(Set<StagingFolderEntry> selection) {
			super(UIText.StagingView_IgnoreFolderMenuLabel);
			this.selection = selection;
		}

		@Override
		public void run() {
			List<IPath> paths = new ArrayList<>();
			for (StagingFolderEntry folder : selection) {
				paths.add(folder.getLocation());
			}
			IgnoreOperationUI operation = new IgnoreOperationUI(paths);
			operation.run();
		}

	}

	private class DeleteAction extends Action {

		private final IStructuredSelection selection;

		DeleteAction(IStructuredSelection selection) {
			super(UIText.StagingView_DeleteItemMenuLabel);
			this.selection = selection;
		}

		@Override
		public void run() {
			DeletePathsOperationUI operation = new DeletePathsOperationUI(
					getSelectedPaths(selection), getSite());
			operation.run();
		}
	}

	private class GlobalDeleteActionHandler extends Action {

		@Override
		public void run() {
			DeletePathsOperationUI operation = new DeletePathsOperationUI(
					getSelectedPaths(getSelection()), getSite());
			operation.run();
		}

		@Override
		public boolean isEnabled() {
			if (!unstagedViewer.getTree().isFocusControl())
				return false;

			IStructuredSelection selection = getSelection();
			if (selection.isEmpty())
				return false;

			for (Object element : selection.toList()) {
				if (!(element instanceof StagingEntry))
					return false;
				StagingEntry entry = (StagingEntry) element;
				if (!entry.getAvailableActions().contains(StagingEntry.Action.DELETE))
					return false;
			}

			return true;
		}

		private IStructuredSelection getSelection() {
			return (IStructuredSelection) unstagedViewer.getSelection();
		}
	}

	private static List<IPath> getSelectedPaths(IStructuredSelection selection) {
		List<IPath> paths = new ArrayList<>();
		Iterator iterator = selection.iterator();
		while (iterator.hasNext()) {
			StagingEntry stagingEntry = (StagingEntry) iterator.next();
			paths.add(stagingEntry.getLocation());
		}
		return paths;
	}

	private boolean selectionIncludesNonWorkspaceResources(ISelection selection) {
		if (!(selection instanceof IStructuredSelection))
			return false;
		IStructuredSelection structuredSelection = (IStructuredSelection) selection;
		Iterator iterator = structuredSelection.iterator();
		while (iterator.hasNext()) {
			Object selectedObject = iterator.next();
			if (!(selectedObject instanceof StagingEntry))
				return false;
			StagingEntry stagingEntry = (StagingEntry) selectedObject;
			IFile file = stagingEntry.getFile();
			if (file == null || !file.isAccessible()) {
				return true;
			}
		}
		return false;
	}

	private void openSelectionInEditor(ISelection s) {
		Repository repo = currentRepository;
		if (repo == null || s.isEmpty() || !(s instanceof IStructuredSelection)) {
			return;
		}
		final IStructuredSelection iss = (IStructuredSelection) s;
		for (Object element : iss.toList()) {
			if (element instanceof StagingEntry) {
				StagingEntry entry = (StagingEntry) element;
				String relativePath = entry.getPath();
				File file = new Path(repo.getWorkTree().getAbsolutePath())
						.append(relativePath).toFile();
				DiffViewer.openFileInEditor(file, -1);
			}
		}
	}

	private static Set<StagingEntry.Action> getAvailableActions(IStructuredSelection selection) {
		Set<StagingEntry.Action> availableActions = EnumSet.noneOf(StagingEntry.Action.class);
		for (Iterator it = selection.iterator(); it.hasNext(); ) {
			StagingEntry stagingEntry = (StagingEntry) it.next();
			if (availableActions.isEmpty())
				availableActions.addAll(stagingEntry.getAvailableActions());
			else
				availableActions.retainAll(stagingEntry.getAvailableActions());
		}
		return availableActions;
	}

	private IAction createItem(String text, final String commandId,
			final IStructuredSelection selection) {
		return new Action(text) {
			@Override
			public void run() {
				CommonUtils.runCommand(commandId, selection);
			}
		};
	}

	private boolean shouldUpdateSelection() {
		return !isDisposed() && !isViewHidden && reactOnSelection;
	}

	private void reactOnSelection(StructuredSelection selection) {
		if (selection.size() != 1 || !shouldUpdateSelection()) {
			return;
		}
		Object firstElement = selection.getFirstElement();
		if (firstElement instanceof RepositoryTreeNode) {
			RepositoryTreeNode repoNode = (RepositoryTreeNode) firstElement;
			if (currentRepository != repoNode.getRepository()) {
				reload(repoNode.getRepository());
			}
		} else if (firstElement instanceof Repository) {
			Repository repo = (Repository) firstElement;
			if (currentRepository != repo) {
				reload(repo);
			}
		} else {
			Repository repo = AdapterUtils.adapt(firstElement,
					Repository.class);
			if (repo != null) {
				if (currentRepository != repo) {
					reload(repo);
				}
			} else {
				IResource resource = AdapterUtils
						.adaptToAnyResource(firstElement);
				if (resource != null) {
					showResource(resource);
				}
			}
		}
	}

	private void showResource(final IResource resource) {
		if (resource == null || !resource.isAccessible()) {
			return;
		}
		Job.getJobManager().cancel(JobFamilies.UPDATE_SELECTION);
		Job job = new Job(UIText.StagingView_GetRepo) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				if (monitor.isCanceled()) {
					return Status.CANCEL_STATUS;
				}
				RepositoryMapping mapping = RepositoryMapping
						.getMapping(resource);
				if (mapping != null) {
					Repository newRep = mapping.getRepository();
					if (newRep != null && newRep != currentRepository) {
						if (monitor.isCanceled()) {
							return Status.CANCEL_STATUS;
						}
						reload(newRep);
					}
				}
				return Status.OK_STATUS;
			}

			@Override
			public boolean belongsTo(Object family) {
				return JobFamilies.UPDATE_SELECTION == family;
			}

			@Override
			public boolean shouldRun() {
				return shouldUpdateSelection();
			}
		};
		job.setSystem(true);
		schedule(job, false);
	}

	private void stage(IStructuredSelection selection) {
		StagingViewContentProvider contentProvider = getContentProvider(unstagedViewer);
		final Repository repository = currentRepository;
		Iterator iterator = selection.iterator();
		final List<String> addPaths = new ArrayList<>();
		final List<String> rmPaths = new ArrayList<>();
		resetPathsToExpand();
		while (iterator.hasNext()) {
			Object element = iterator.next();
			if (element instanceof StagingEntry) {
				StagingEntry entry = (StagingEntry) element;
				selectEntryForStaging(entry, addPaths, rmPaths);
				addPathAndParentPaths(entry.getParentPath(), pathsToExpandInStaged);
			} else if (element instanceof StagingFolderEntry) {
				StagingFolderEntry folder = (StagingFolderEntry) element;
				List<StagingEntry> entries = contentProvider
						.getStagingEntriesFiltered(folder);
				for (StagingEntry entry : entries)
					selectEntryForStaging(entry, addPaths, rmPaths);
				addExpandedPathsBelowFolder(folder, unstagedViewer,
						pathsToExpandInStaged);
			} else {
				IResource resource = AdapterUtils.adaptToAnyResource(element);
				if (resource != null) {
					RepositoryMapping mapping = RepositoryMapping.getMapping(resource);
					if (mapping != null && mapping.getRepository() == currentRepository) {
						String path = mapping.getRepoRelativePath(resource);
						if ("".equals(path)) //$NON-NLS-1$
							addPaths.add("."); //$NON-NLS-1$
						else
							addPaths.add(path);
					}
				}
			}
		}

		if (!addPaths.isEmpty()) {
			Job addJob = new Job(UIText.StagingView_AddJob) {
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					try (Git git = new Git(repository)) {
						AddCommand add = git.add();
						for (String addPath : addPaths)
							add.addFilepattern(addPath);
						add.call();
					} catch (NoFilepatternException e1) {
					} catch (JGitInternalException e1) {
						Activator.handleError(e1.getCause().getMessage(),
								e1.getCause(), true);
					} catch (Exception e1) {
						Activator.handleError(e1.getMessage(), e1, true);
					}
					return Status.OK_STATUS;
				}

				@Override
				public boolean belongsTo(Object family) {
					return family == JobFamilies.ADD_TO_INDEX;
				}
			};

			schedule(addJob, true);
		}

		if (!rmPaths.isEmpty()) {
			Job removeJob = new Job(UIText.StagingView_RemoveJob) {
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					try (Git git = new Git(repository)) {
						RmCommand rm = git.rm().setCached(true);
						for (String rmPath : rmPaths)
							rm.addFilepattern(rmPath);
						rm.call();
					} catch (NoFilepatternException e) {
					} catch (JGitInternalException e) {
						Activator.handleError(e.getCause().getMessage(),
								e.getCause(), true);
					} catch (Exception e) {
						Activator.handleError(e.getMessage(), e, true);
					}
					return Status.OK_STATUS;
				}

				@Override
				public boolean belongsTo(Object family) {
					return family == JobFamilies.REMOVE_FROM_INDEX;
				}
			};

			schedule(removeJob, true);
		}
	}

	private void selectEntryForStaging(StagingEntry entry,
			List<String> addPaths, List<String> rmPaths) {
		switch (entry.getState()) {
		case ADDED:
		case CHANGED:
		case REMOVED:
			break;
		case CONFLICTING:
		case MODIFIED:
		case MODIFIED_AND_CHANGED:
		case MODIFIED_AND_ADDED:
		case UNTRACKED:
			addPaths.add(entry.getPath());
			break;
		case MISSING:
		case MISSING_AND_CHANGED:
			rmPaths.add(entry.getPath());
			break;
		}
	}

	private void unstage(IStructuredSelection selection) {
		if (selection.isEmpty())
			return;

		final List<String> paths = processUnstageSelection(selection);
		if (paths.isEmpty())
			return;

		final Repository repository = currentRepository;

		Job resetJob = new Job(UIText.StagingView_ResetJob) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try (Git git = new Git(repository)) {
					ResetCommand reset = git.reset();
					for (String path : paths)
						reset.addPath(path);
					reset.call();
				} catch (GitAPIException e) {
					Activator.handleError(e.getMessage(), e, true);
				}
				return Status.OK_STATUS;
			}

			@Override
			public boolean belongsTo(Object family) {
				return family == JobFamilies.RESET;
			}
		};
		schedule(resetJob, true);
	}

	private List<String> processUnstageSelection(IStructuredSelection selection) {
		List<String> paths = new ArrayList<>();
		resetPathsToExpand();
		for (Object element : selection.toList()) {
			if (element instanceof StagingEntry) {
				StagingEntry entry = (StagingEntry) element;
				addUnstagePath(entry, paths);
				addPathAndParentPaths(entry.getParentPath(), pathsToExpandInUnstaged);
			} else if (element instanceof StagingFolderEntry) {
				StagingFolderEntry folder = (StagingFolderEntry) element;
				List<StagingEntry> entries = getContentProvider(stagedViewer)
						.getStagingEntriesFiltered(folder);
				for (StagingEntry entry : entries)
					addUnstagePath(entry, paths);
				addExpandedPathsBelowFolder(folder, stagedViewer,
						pathsToExpandInUnstaged);
			}
		}
		return paths;
	}

	private void addUnstagePath(StagingEntry entry, List<String> paths) {
		switch (entry.getState()) {
		case ADDED:
		case CHANGED:
		case REMOVED:
			paths.add(entry.getPath());
			return;
		default:
		}
	}

	private void resetPathsToExpand() {
		pathsToExpandInStaged = new HashSet<>();
		pathsToExpandInUnstaged = new HashSet<>();
	}

	private static void addExpandedPathsBelowFolder(StagingFolderEntry folder,
			TreeViewer treeViewer, Set<IPath> addToSet) {
		Object[] expandedElements = treeViewer.getVisibleExpandedElements();
		for (Object expandedElement : expandedElements) {
			if (expandedElement instanceof StagingFolderEntry) {
				StagingFolderEntry expandedFolder = (StagingFolderEntry) expandedElement;
				if (folder.getPath().isPrefixOf(
						expandedFolder.getPath()))
					addPathAndParentPaths(expandedFolder.getPath(), addToSet);
			}
		}
	}

	private static void addPathAndParentPaths(IPath initialPath, Set<IPath> addToSet) {
		for (IPath p = initialPath; p.segmentCount() >= 1; p = p
				.removeLastSegments(1))
			addToSet.add(p);
	}

	private boolean isValidRepo(final Repository repository) {
		return repository != null
				&& !repository.isBare()
				&& repository.getWorkTree().exists();
	}

	private void clearRepository(@Nullable Repository repository) {
		saveCommitMessageComponentState();
		currentRepository = null;
		StagingViewUpdate update = new StagingViewUpdate(null, null, null);
		setStagingViewerInput(unstagedViewer, update, null, null);
		setStagingViewerInput(stagedViewer, update, null, null);
		enableCommitWidgets(false);
		refreshAction.setEnabled(false);
		updateSectionText();
		if (repository != null && repository.isBare()) {
			form.setText(UIText.StagingView_BareRepoSelection);
		} else {
			form.setText(UIText.StagingView_NoSelectionTitle);
		}
		updateIgnoreErrorsButtonVisibility();
	}

	protected void updateRebaseButtonVisibility(final boolean isRebasing) {
		asyncExec(new Runnable() {
			@Override
			public void run() {
				if (isDisposed())
					return;
				showControl(rebaseSection, isRebasing);
				rebaseSection.getParent().layout(true);
			}
		});
	}

	private static void showControl(Control c, final boolean show) {
		c.setVisible(show);
		GridData g = (GridData) c.getLayoutData();
		g.exclude = !show;
	}

	public void setAmending(boolean isAmending) {
		if (isDisposed())
			return;
		if (amendPreviousCommitAction.isChecked() != isAmending) {
			amendPreviousCommitAction.setChecked(isAmending);
			amendPreviousCommitAction.run();
		}
	}

	public void setCommitMessage(String message) {
		commitMessageText.setText(message);
	}

	public void reload(final Repository repository) {
		if (isDisposed()) {
			return;
		}
		if (repository == null) {
			asyncExec(new Runnable() {
				@Override
				public void run() {
					clearRepository(null);
				}
			});
			return;
		}

		if (!isValidRepo(repository)) {
			asyncExec(new Runnable() {
				@Override
				public void run() {
					clearRepository(repository);
				}
			});
			return;
		}

		final boolean repositoryChanged = currentRepository != repository;
		currentRepository = repository;

		asyncExec(new Runnable() {

			@Override
			public void run() {
				if (isDisposed()) {
					return;
				}

				final IndexDiffData indexDiff = doReload(repository);
				boolean indexDiffAvailable = indexDiffAvailable(indexDiff);
				boolean noConflicts = noConflicts(indexDiff);

				if (repositoryChanged) {
					resetPathsToExpand();
					if (refsChangedListener != null)
						refsChangedListener.remove();
					refsChangedListener = repository.getListenerList()
							.addRefsChangedListener(new RefsChangedListener() {

								@Override
								public void onRefsChanged(RefsChangedEvent event) {
									updateRebaseButtonVisibility(repository
											.getRepositoryState().isRebasing());
								}

							});
				}
				final StagingViewUpdate update = new StagingViewUpdate(repository, indexDiff, null);
				Object[] unstagedExpanded = unstagedViewer.getVisibleExpandedElements();
				Object[] stagedExpanded = stagedViewer.getVisibleExpandedElements();

				int unstagedElementsCount = updateAutoExpand(unstagedViewer,
						getUnstaged(indexDiff));
				int stagedElementsCount = updateAutoExpand(stagedViewer,
						getStaged(indexDiff));
				int elementsCount = unstagedElementsCount + stagedElementsCount;

				if (elementsCount > getMaxLimitForListMode()) {
					listPresentationAction.setEnabled(false);
					if (presentation == Presentation.LIST) {
						compactTreePresentationAction.setChecked(true);
						switchToCompactModeInternal(true);
					} else {
						setExpandCollapseActionsVisible(false,
								unstagedElementsCount <= getMaxLimitForListMode(),
								true);
						setExpandCollapseActionsVisible(true,
								stagedElementsCount <= getMaxLimitForListMode(),
								true);
					}
				} else {
					listPresentationAction.setEnabled(true);
					boolean changed = getPreferenceStore().getBoolean(
							UIPreferences.STAGING_VIEW_PRESENTATION_CHANGED);
					if (changed) {
						listPresentationAction.setChecked(true);
						switchToListMode();
					} else if (presentation != Presentation.LIST) {
						setExpandCollapseActionsVisible(false, true, true);
						setExpandCollapseActionsVisible(true, true, true);
					}
				}

				setStagingViewerInput(unstagedViewer, update, unstagedExpanded,
						pathsToExpandInUnstaged);
				setStagingViewerInput(stagedViewer, update, stagedExpanded,
						pathsToExpandInStaged);
				resetPathsToExpand();
				refreshAction.setEnabled(true);

				updateRebaseButtonVisibility(repository.getRepositoryState()
						.isRebasing());

				updateIgnoreErrorsButtonVisibility();

				boolean rebaseContinueEnabled = indexDiffAvailable
						&& repository.getRepositoryState().isRebasing()
						&& noConflicts;
				rebaseContinueButton.setEnabled(rebaseContinueEnabled);

				form.setText(GitLabels.getStyledLabelSafe(repository).toString());
				updateCommitMessageComponent(repositoryChanged, indexDiffAvailable);
				enableCommitWidgets(indexDiffAvailable && noConflicts);

				updateCommitButtons();
				updateSectionText();
			}

		});
	}

	private int getMaxLimitForListMode() {
		return Activator.getDefault().getPreferenceStore()
				.getInt(UIPreferences.STAGING_VIEW_MAX_LIMIT_LIST_MODE);
	}

	private static int getUnstaged(@Nullable IndexDiffData indexDiff) {
		if (indexDiff == null) {
			return 0;
		}
		int size = indexDiff.getUntracked().size();
		size += indexDiff.getMissing().size();
		size += indexDiff.getModified().size();
		size += indexDiff.getConflicting().size();
		return size;
	}

	private static int getStaged(@Nullable IndexDiffData indexDiff) {
		if (indexDiff == null) {
			return 0;
		}
		int size = indexDiff.getAdded().size();
		size += indexDiff.getChanged().size();
		size += indexDiff.getRemoved().size();
		return size;
	}

	private int updateAutoExpand(TreeViewer viewer, int newSize) {
		if (newSize > getMaxLimitForListMode()) {
			disableAutoExpand(viewer);
		}
		return newSize;
	}

	private void switchToCompactModeInternal(boolean auto) {
		setPresentation(Presentation.COMPACT_TREE, auto);
		listPresentationAction.setChecked(false);
		treePresentationAction.setChecked(false);
		if (auto) {
			setExpandCollapseActionsVisible(false, false, true);
			setExpandCollapseActionsVisible(true, false, true);
		} else {
			setExpandCollapseActionsVisible(false, isExpandAllowed(false),
					true);
			setExpandCollapseActionsVisible(true, isExpandAllowed(true), true);
		}
	}

	private void switchToListMode() {
		setPresentation(Presentation.LIST, false);
		treePresentationAction.setChecked(false);
		compactTreePresentationAction.setChecked(false);
		setExpandCollapseActionsVisible(false, false, false);
		setExpandCollapseActionsVisible(true, false, false);
	}

	private static boolean noConflicts(IndexDiffData indexDiff) {
		return indexDiff == null ? true : indexDiff.getConflicting().isEmpty();
	}

	private static boolean indexDiffAvailable(IndexDiffData indexDiff) {
		return indexDiff == null ? false : true;
	}

	private boolean hasErrorsOrWarnings() {
		return getPreferenceStore()
				.getBoolean(UIPreferences.WARN_BEFORE_COMMITTING)
						? (getProblemsSeverity() >= Integer
								.parseInt(getPreferenceStore()
						.getString(UIPreferences.WARN_BEFORE_COMMITTING_LEVEL))
				&& !ignoreErrors.getSelection()) : false;
	}

	private boolean isCommitBlocked() {
		return getPreferenceStore()
				.getBoolean(UIPreferences.WARN_BEFORE_COMMITTING)
				&& getPreferenceStore().getBoolean(UIPreferences.BLOCK_COMMIT)
						? (getProblemsSeverity() >= Integer
								.parseInt(getPreferenceStore().getString(
										UIPreferences.BLOCK_COMMIT_LEVEL))
								&& !ignoreErrors.getSelection())
						: false;
	}

	private IndexDiffData doReload(@NonNull	final Repository repository) {
		IndexDiffCacheEntry entry = org.eclipse.egit.core.Activator.getDefault()
				.getIndexDiffCache().getIndexDiffCacheEntry(repository);

		if(cacheEntry != null && cacheEntry != entry)
			cacheEntry.removeIndexDiffChangedListener(myIndexDiffListener);

		cacheEntry = entry;
		cacheEntry.addIndexDiffChangedListener(myIndexDiffListener);

		return cacheEntry.getIndexDiff();
	}

	private void expandPreviousExpandedAndPaths(Object[] previous,
			TreeViewer viewer, Set<IPath> additionalPaths) {

		StagingViewContentProvider stagedContentProvider = getContentProvider(
				viewer);
		int count = stagedContentProvider.getCount();
		updateAutoExpand(viewer, count);

		if (viewer.getAutoExpandLevel() == AbstractTreeViewer.ALL_LEVELS) {
			return;
		}

		if (getPresentation() == Presentation.LIST)
			return;

		Set<IPath> paths = new HashSet<>(additionalPaths);
		for (Object element : previous) {
			if (element instanceof StagingFolderEntry) {
				addPathAndParentPaths(((StagingFolderEntry) element).getPath(), paths);
			}
		}
		for (Object element : viewer.getVisibleExpandedElements()) {
			if (element instanceof StagingFolderEntry) {
				addPathAndParentPaths(((StagingFolderEntry) element).getPath(),
						paths);
			}
		}

		List<StagingFolderEntry> expand = new ArrayList<>();

		calculateNodesToExpand(paths, stagedContentProvider.getElements(null),
				expand);
		viewer.setExpandedElements(expand.toArray());
	}

	private void calculateNodesToExpand(Set<IPath> paths, Object[] elements,
			List<StagingFolderEntry> result) {
		if (elements == null)
			return;

		for (Object element : elements) {
			if (element instanceof StagingFolderEntry) {
				StagingFolderEntry folder = (StagingFolderEntry) element;
				if (paths.contains(folder.getPath())) {
					result.add(folder);
					calculateNodesToExpand(paths, folder.getChildren(), result);
				}
			}
		}
	}

	private void clearCommitMessageToggles() {
		amendPreviousCommitAction.setChecked(false);
		addChangeIdAction.setChecked(false);
		signedOffByAction.setChecked(false);
	}

	void updateCommitMessageComponent(boolean repositoryChanged, boolean indexDiffAvailable) {
		if (repositoryChanged)
			if (commitMessageComponent.isAmending()
					|| userEnteredCommitMessage())
				saveCommitMessageComponentState();
			else
				deleteCommitMessageComponentState();
		if (!indexDiffAvailable)
			return; // only try to restore the stored repo commit message if

		CommitHelper helper = new CommitHelper(currentRepository);
		CommitMessageComponentState oldState = null;
		if (repositoryChanged
				|| commitMessageComponent.getRepository() != currentRepository) {
			oldState = loadCommitMessageComponentState();
			commitMessageComponent.setRepository(currentRepository);
			if (oldState == null)
				loadInitialState(helper);
			else
				loadExistingState(helper, oldState);
		} else { // repository did not change
			if (!commitMessageComponent.getHeadCommit().equals(
					helper.getPreviousCommit())
					|| !commitMessageComponent.isAmending()) {
				if (!commitMessageComponent.isAmending()
						&& userEnteredCommitMessage())
					addHeadChangedWarning(commitMessageComponent
							.getCommitMessage());
				else
					loadInitialState(helper);
			}
		}
		amendPreviousCommitAction.setChecked(commitMessageComponent
				.isAmending());
		amendPreviousCommitAction.setEnabled(helper.amendAllowed());
		updateMessage();
	}

	public void resetCommitMessageComponent() {
		if (currentRepository != null) {
			String commitMessage = commitMessageComponent.getCommitMessage();
			if (commitMessage.trim().length() > 0) {
				CommitMessageHistory.saveCommitHistory(commitMessage);
			}
			loadInitialState(new CommitHelper(currentRepository));
		}
	}

	private void loadExistingState(CommitHelper helper,
			CommitMessageComponentState oldState) {
		boolean headCommitChanged = !oldState.getHeadCommit().equals(
				getCommitId(helper.getPreviousCommit()));
		commitMessageComponent.enableListeners(false);
		commitMessageComponent.setAuthor(oldState.getAuthor());
		if (headCommitChanged)
			addHeadChangedWarning(oldState.getCommitMessage());
		else
			commitMessageComponent
					.setCommitMessage(oldState.getCommitMessage());
		commitMessageComponent.setCommitter(oldState.getCommitter());
		commitMessageComponent.setHeadCommit(getCommitId(helper
				.getPreviousCommit()));
		commitMessageComponent.setCommitAllowed(helper.canCommit());
		commitMessageComponent.setCannotCommitMessage(helper.getCannotCommitMessage());
		boolean amendAllowed = helper.amendAllowed();
		commitMessageComponent.setAmendAllowed(amendAllowed);
		if (!amendAllowed)
			commitMessageComponent.setAmending(false);
		else if (!headCommitChanged && oldState.getAmend())
			commitMessageComponent.setAmending(true);
		else
			commitMessageComponent.setAmending(false);
		commitMessageComponent.updateUIFromState();
		commitMessageComponent.updateSignedOffAndChangeIdButton();
		commitMessageComponent.enableListeners(true);
	}

	private void addHeadChangedWarning(String commitMessage) {
		if (!commitMessage.startsWith(UIText.StagingView_headCommitChanged)) {
			String message = UIText.StagingView_headCommitChanged
					+ Text.DELIMITER + Text.DELIMITER + commitMessage;
			commitMessageComponent.setCommitMessage(message);
		}
	}

	private void loadInitialState(CommitHelper helper) {
		commitMessageComponent.enableListeners(false);
		commitMessageComponent.resetState();
		commitMessageComponent.setAuthor(helper.getAuthor());
		commitMessageComponent.setCommitMessage(helper.getCommitMessage());
		commitMessageComponent.setCommitter(helper.getCommitter());
		commitMessageComponent.setHeadCommit(getCommitId(helper
				.getPreviousCommit()));
		commitMessageComponent.setCommitAllowed(helper.canCommit());
		commitMessageComponent.setCannotCommitMessage(helper.getCannotCommitMessage());
		commitMessageComponent.setAmendAllowed(helper.amendAllowed());
		commitMessageComponent.setAmending(false);
		commitMessageComponent.setDefaults();
		commitMessageComponent.updateUI();
		commitMessageComponent.enableListeners(true);
	}

	private boolean userEnteredCommitMessage() {
		if (commitMessageComponent.getRepository() == null)
			return false;
		String message = commitMessageComponent.getCommitMessage().replace(
				UIText.StagingView_headCommitChanged, ""); //$NON-NLS-1$
		if (message == null || message.trim().length() == 0)
			return false;

		String chIdLine = "Change-Id: I" + ObjectId.zeroId().name(); //$NON-NLS-1$
		Repository repo = currentRepository;
		if (repo != null && GerritUtil.getCreateChangeId(repo.getConfig())
				&& commitMessageComponent.getCreateChangeId()) {
			if (message.trim().equals(chIdLine))
				return false;

			message = message.replace(chIdLine, ""); //$NON-NLS-1$
		}

		if (org.eclipse.egit.ui.Activator.getDefault().getPreferenceStore()
				.getBoolean(UIPreferences.COMMIT_DIALOG_SIGNED_OFF_BY)
				&& commitMessageComponent.isSignedOff()
				&& message.trim().equals(
						Constants.SIGNED_OFF_BY_TAG
								+ commitMessageComponent.getCommitter()))
			return false;

		return true;
	}

	private ObjectId getCommitId(RevCommit commit) {
		if (commit == null)
			return ObjectId.zeroId();
		return commit.getId();
	}

	private void saveCommitMessageComponentState() {
		final Repository repo = commitMessageComponent.getRepository();
		if (repo != null)
			CommitMessageComponentStateManager.persistState(repo,
					commitMessageComponent.getState());
	}

	private void deleteCommitMessageComponentState() {
		if (commitMessageComponent.getRepository() != null)
			CommitMessageComponentStateManager
					.deleteState(commitMessageComponent.getRepository());
	}

	private CommitMessageComponentState loadCommitMessageComponentState() {
		return CommitMessageComponentStateManager.loadState(currentRepository);
	}

	private Collection<String> getStagedFileNames() {
		StagingViewContentProvider stagedContentProvider = getContentProvider(stagedViewer);
		StagingEntry[] entries = stagedContentProvider.getStagingEntries();
		List<String> files = new ArrayList<>();
		for (StagingEntry entry : entries)
			files.add(entry.getPath());
		return files;
	}

	private void commit(boolean pushUpstream) {
		if (!isCommitWithoutFilesAllowed()) {
			MessageDialog.openError(getSite().getShell(),
					UIText.StagingView_committingNotPossible,
					UIText.StagingView_noStagedFiles);
			return;
		}
		if (!commitMessageComponent.checkCommitInfo())
			return;

		if (!UIUtils.saveAllEditors(currentRepository,
				UIText.StagingView_cancelCommitAfterSaving))
			return;

		String commitMessage = commitMessageComponent.getCommitMessage();
		CommitOperation commitOperation = null;
		try {
			commitOperation = new CommitOperation(currentRepository,
					commitMessageComponent.getAuthor(),
					commitMessageComponent.getCommitter(),
					commitMessage);
		} catch (CoreException e) {
			Activator.handleError(UIText.StagingView_commitFailed, e, true);
			return;
		}
		if (amendPreviousCommitAction.isChecked())
			commitOperation.setAmending(true);
		final boolean gerritMode = addChangeIdAction.isChecked();
		commitOperation.setComputeChangeId(gerritMode);

		PushMode pushMode = null;
		if (pushUpstream) {
			pushMode = gerritMode ? PushMode.GERRIT : PushMode.UPSTREAM;
		}
		final Job commitJob = new CommitJob(currentRepository, commitOperation)
				.setOpenCommitEditor(openNewCommitsAction.isChecked())
				.setPushUpstream(pushMode);

		enableAllWidgets(false);
		commitJob.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				asyncExec(new Runnable() {
					@Override
					public void run() {
						enableAllWidgets(true);
						if (commitJob.getResult().isOK()) {
							commitMessageText.setText(EMPTY_STRING);
						}
					}
				});
			}
		});

		schedule(commitJob, true);

		CommitMessageHistory.saveCommitHistory(commitMessage);
		clearCommitMessageToggles();
	}

	private void schedule(Job job, boolean useRepositoryRule) {
		if (useRepositoryRule)
			job.setRule(RuleUtil.getRule(currentRepository));
		IWorkbenchSiteProgressService service = CommonUtils.getService(getSite(), IWorkbenchSiteProgressService.class);
		if (service != null)
			service.schedule(job, 0, true);
		else
			job.schedule();
	}

	private boolean isCommitWithoutFilesAllowed() {
		if (stagedViewer.getTree().getItemCount() > 0)
			return true;

		if (amendPreviousCommitAction.isChecked())
			return true;

		return CommitHelper.isCommitWithoutFilesAllowed(currentRepository);
	}

	@Override
	public void setFocus() {
		Tree tree = unstagedViewer.getTree();
		if (tree.getItemCount() > 0 && !isAutoStageOnCommitEnabled()) {
			unstagedViewer.getControl().setFocus();
			return;
		}
		commitMessageText.setFocus();
	}

	private boolean isAutoStageOnCommitEnabled() {
		IPreferenceStore uiPreferences = Activator.getDefault()
				.getPreferenceStore();
		return uiPreferences.getBoolean(UIPreferences.AUTO_STAGE_ON_COMMIT);
	}

	@Override
	public void dispose() {
		super.dispose();

		ISelectionService srv = CommonUtils.getService(getSite(), ISelectionService.class);
		srv.removePostSelectionListener(selectionChangedListener);
		CommonUtils.getService(getSite(), IPartService.class)
				.removePartListener(partListener);

		if (cacheEntry != null) {
			cacheEntry.removeIndexDiffChangedListener(myIndexDiffListener);
		}

		if (undoRedoActionGroup != null) {
			undoRedoActionGroup.dispose();
		}

		InstanceScope.INSTANCE.getNode(
				org.eclipse.egit.core.Activator.getPluginId())
				.removePreferenceChangeListener(prefListener);
		if (refsChangedListener != null) {
			refsChangedListener.remove();
		}

		getPreferenceStore().removePropertyChangeListener(uiPrefsListener);

		getDialogSettings().put(STORE_SORT_STATE, sortAction.isChecked());

		currentRepository = null;

		disposed = true;
	}

	private boolean isDisposed() {
		return disposed;
	}

	private static void syncExec(Runnable runnable) {
		PlatformUI.getWorkbench().getDisplay().syncExec(runnable);
	}

	private void asyncExec(Runnable runnable) {
		PlatformUI.getWorkbench().getDisplay().asyncExec(runnable);
	}

	private static class StagingEntryComparator extends ViewerComparator {

		private boolean alphabeticSort;

		private Comparator<String> comparator;

		private boolean fileNamesFirst;

		private StagingEntryComparator(boolean alphabeticSort,
				boolean fileNamesFirst) {
			this.alphabeticSort = alphabeticSort;
			this.setFileNamesFirst(fileNamesFirst);
			comparator = CommonUtils.STRING_ASCENDING_COMPARATOR;
		}

		public boolean isFileNamesFirst() {
			return fileNamesFirst;
		}

		public void setFileNamesFirst(boolean fileNamesFirst) {
			this.fileNamesFirst = fileNamesFirst;
		}

		private void setAlphabeticSort(boolean sort) {
			this.alphabeticSort = sort;
		}

		private boolean isAlphabeticSort() {
			return alphabeticSort;
		}

		@Override
		public int category(Object element) {
			if (!isAlphabeticSort()) {
				StagingEntry stagingEntry = getStagingEntry(element);
				if (stagingEntry != null) {
					return getState(stagingEntry);
				}
			}
			return super.category(element);
		}

		@Override
		public int compare(Viewer viewer, Object e1, Object e2) {
			int cat1 = category(e1);
			int cat2 = category(e2);

			if (cat1 != cat2) {
				return cat1 - cat2;
			}

			String name1 = getStagingEntryText(e1);
			String name2 = getStagingEntryText(e2);

			return comparator.compare(name1, name2);
		}

		private String getStagingEntryText(Object element) {
			String text = ""; //$NON-NLS-1$
			StagingEntry stagingEntry = getStagingEntry(element);
			if (stagingEntry != null) {
				if (isFileNamesFirst()) {
					text = stagingEntry.getName();
				} else {
					text = stagingEntry.getPath();
				}
			}
			return text;
		}

		@Nullable
		private StagingEntry getStagingEntry(Object element) {
			StagingEntry entry = null;
			if (element instanceof StagingEntry) {
				entry = (StagingEntry) element;
			}
			if (element instanceof TreeItem) {
				TreeItem item = (TreeItem) element;
				if (item.getData() instanceof StagingEntry) {
					entry = (StagingEntry) item.getData();
				}
			}
			return entry;
		}

		private int getState(StagingEntry entry) {
			switch (entry.getState()) {
			case CONFLICTING:
				return 1;
			case MODIFIED:
				return 2;
			case MODIFIED_AND_ADDED:
				return 3;
			case MODIFIED_AND_CHANGED:
				return 4;
			case ADDED:
				return 5;
			case CHANGED:
				return 6;
			case MISSING:
				return 7;
			case MISSING_AND_CHANGED:
				return 8;
			case REMOVED:
				return 9;
			case UNTRACKED:
				return 10;
			default:
				return super.category(entry);
			}
		}

	}
}
