package org.eclipse.egit.ui.internal.commit;

import static java.util.Arrays.asList;
import static org.eclipse.egit.ui.UIPreferences.THEME_DiffAddBackgroundColor;
import static org.eclipse.egit.ui.UIPreferences.THEME_DiffRemoveBackgroundColor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.egit.core.internal.IRepositoryCommit;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.JobFamilies;
import org.eclipse.egit.ui.UIUtils;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.egit.ui.internal.commit.DiffRegionFormatter.DiffRegion;
import org.eclipse.egit.ui.internal.commit.DiffRegionFormatter.FileDiffRegion;
import org.eclipse.egit.ui.internal.history.FileDiff;
import org.eclipse.egit.ui.internal.repository.RepositoriesView;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.AnnotationModel;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.IAnnotationModelExtension;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.IVerticalRulerColumn;
import org.eclipse.jface.text.source.projection.ProjectionAnnotation;
import org.eclipse.jface.text.source.projection.ProjectionSupport;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.team.ui.history.IHistoryView;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.IShowInSource;
import org.eclipse.ui.part.IShowInTargetList;
import org.eclipse.ui.part.ShowInContext;
import org.eclipse.ui.progress.UIJob;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;
import org.eclipse.ui.texteditor.AbstractDocumentProvider;
import org.eclipse.ui.texteditor.ChainedPreferenceStore;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditorActionConstants;
import org.eclipse.ui.themes.IThemeManager;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

public class DiffEditor extends TextEditor
		implements IShowInSource, IShowInTargetList {

	public static final String EDITOR_ID = "org.eclipse.egit.ui.diffEditor"; //$NON-NLS-1$

	private static final String ADD_ANNOTATION_TYPE = "org.eclipse.egit.ui.commitEditor.diffAdded"; //$NON-NLS-1$

	private static final String REMOVE_ANNOTATION_TYPE = "org.eclipse.egit.ui.commitEditor.diffRemoved"; //$NON-NLS-1$

	private DiffEditorOutlinePage outlinePage;

	private Annotation[] currentFoldingAnnotations;

	private Annotation[] currentOverviewAnnotations;

	private ThemePreferenceStore overviewStore;

	private FileDiffRegion currentFileDiffRange;

	private OldNewLogicalLineNumberRulerColumn lineNumberColumn;

	private boolean plainLineNumbers = false;

	public DiffEditor() {
		setPartName(UIText.DiffEditor_Title);
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		if (IContentOutlinePage.class.equals(adapter)) {
			if (outlinePage == null) {
				outlinePage = createOutlinePage();
				outlinePage.setInput(
						getDocumentProvider().getDocument(getEditorInput()));
				if (currentFileDiffRange != null) {
					outlinePage.setSelection(
							new StructuredSelection(currentFileDiffRange));
				}
			}
			return adapter.cast(outlinePage);
		}
		return super.getAdapter(adapter);
	}

	private DiffEditorOutlinePage createOutlinePage() {
		DiffEditorOutlinePage page = new DiffEditorOutlinePage();
		page.addSelectionChangedListener(
				event -> setSelectionAndActivate(event.getSelection(), false));
		page.addOpenListener(
				event -> setSelectionAndActivate(event.getSelection(), true));
		return page;
	}

	protected DiffEditorOutlinePage getOutlinePage() {
		return outlinePage;
	}

	protected void setSelectionAndActivate(ISelection selection,
			boolean activate) {
		if (activate) {
			getSite().getPage().activate(this);
		}
		doSetSelection(selection);
	}

	@Override
	public void dispose() {
		if (overviewStore != null) {
			overviewStore.dispose();
			overviewStore = null;
		}
		super.dispose();
	}


	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	protected ISourceViewer createSourceViewer(Composite parent,
			IVerticalRuler ruler, int styles) {
		DiffViewer viewer = new DiffViewer(parent, ruler, getOverviewRuler(),
				isOverviewRulerVisible(), styles) {
			@Override
			protected void setFont(Font font) {
			}
		};
		getSourceViewerDecorationSupport(viewer);
		ProjectionSupport projector = new ProjectionSupport(viewer,
				getAnnotationAccess(), getSharedColors());
		projector.install();
		viewer.getTextWidget().addCaretListener(event -> {
			if (outlinePage != null) {
				FileDiffRegion region = getFileDiffRange(event.caretOffset);
				if (region != null && !region.equals(currentFileDiffRange)) {
					currentFileDiffRange = region;
					outlinePage.setSelection(new StructuredSelection(region));
				} else {
					currentFileDiffRange = region;
				}
			}
		});
		return viewer;
	}

	@Override
	protected IVerticalRulerColumn createLineNumberRulerColumn() {
		lineNumberColumn = new OldNewLogicalLineNumberRulerColumn(
				plainLineNumbers);
		initializeLineNumberRulerColumn(lineNumberColumn);
		return lineNumberColumn;
	}

	@Override
	protected void initializeEditor() {
		super.initializeEditor();
		overviewStore = new ThemePreferenceStore();
		setPreferenceStore(new ChainedPreferenceStore(new IPreferenceStore[] {
				overviewStore, EditorsUI.getPreferenceStore() }));
		setDocumentProvider(new DiffDocumentProvider());
		setSourceViewerConfiguration(
				new DiffViewer.Configuration(getPreferenceStore()) {
					@Override
					public IReconciler getReconciler(
							ISourceViewer sourceViewer) {
						return null;
					}
				});
	}

	@Override
	protected void doSetInput(IEditorInput input) throws CoreException {
		super.doSetInput(input);
		currentFileDiffRange = null;
		if (input instanceof DiffEditorInput) {
			DiffEditorInput diffInput = (DiffEditorInput) input;
			if (diffInput.getDocument() != null) {
				setFolding();
				FileDiffRegion region = getFileDiffRange(0);
				currentFileDiffRange = region;
				setOverviewAnnotations();
			} else {
				formatDiff(diffInput);
			}
		}
		if (outlinePage != null) {
			outlinePage.setInput(getDocumentProvider().getDocument(input));
			if (currentFileDiffRange != null) {
				outlinePage.setSelection(
						new StructuredSelection(currentFileDiffRange));
			}
		}
	}

	@Override
	protected void doSetSelection(ISelection selection) {
		if (!selection.isEmpty() && selection instanceof StructuredSelection) {
			Object selected = ((StructuredSelection) selection)
					.getFirstElement();
			if (selected instanceof FileDiffRegion) {
				FileDiffRegion newRange = (FileDiffRegion) selected;
				if (!newRange.equals(currentFileDiffRange)) {
					currentFileDiffRange = newRange;
					selectAndReveal(newRange.getOffset(), 0);
				}
				return;
			}
		}
		super.doSetSelection(selection);
	}

	@Override
	protected void editorContextMenuAboutToShow(IMenuManager menu) {
		super.editorContextMenuAboutToShow(menu);
		addAction(menu, ITextEditorActionConstants.GROUP_COPY,
				ITextEditorActionConstants.SELECT_ALL);
		menu.remove(ITextEditorActionConstants.SHIFT_RIGHT);
		menu.remove(ITextEditorActionConstants.SHIFT_LEFT);
	}

	@Override
	protected void rulerContextMenuAboutToShow(IMenuManager menu) {
		super.rulerContextMenuAboutToShow(menu);
		IContributionItem showLineNumbers = menu
				.find(ITextEditorActionConstants.LINENUMBERS_TOGGLE);
		boolean isShowingLineNumbers = EditorsUI.getPreferenceStore()
				.getBoolean(
						AbstractDecoratedTextEditorPreferenceConstants.EDITOR_LINE_NUMBER_RULER);
		if (showLineNumbers instanceof ActionContributionItem) {
			((ActionContributionItem) showLineNumbers).getAction()
					.setChecked(isShowingLineNumbers);
		}
		if (isShowingLineNumbers) {
			boolean plain = lineNumberColumn.isPlain();
			IAction togglePlain = new Action(
					UIText.DiffEditor_ToggleLineNumbers,
					IAction.AS_CHECK_BOX) {

				@Override
				public void run() {
					plainLineNumbers = !plain;
					lineNumberColumn.setPlain(!plain);
				}
			};
			togglePlain.setChecked(!plain);
			menu.appendToGroup(ITextEditorActionConstants.GROUP_RULERS,
					togglePlain);
		}
	}


	@Override
	public ShowInContext getShowInContext() {
		IRepositoryCommit commit = Adapters.adapt(getEditorInput(),
				IRepositoryCommit.class);
		if (commit != null) {
			return new ShowInContext(getEditorInput(),
					new StructuredSelection(commit));
		}
		return null;
	}

	@Override
	public String[] getShowInTargetIds() {
		return new String[] { IHistoryView.VIEW_ID, RepositoriesView.VIEW_ID };
	}


	private void setFolding() {
		ProjectionViewer viewer = (ProjectionViewer) getSourceViewer();
		if (viewer == null) {
			return;
		}
		IDocument document = viewer.getDocument();
		if (document instanceof DiffDocument) {
			FileDiffRegion[] regions = ((DiffDocument) document)
					.getFileRegions();
			if (regions == null || regions.length <= 1) {
				viewer.disableProjection();
				return;
			}
			viewer.enableProjection();
			Map<Annotation, Position> newAnnotations = new HashMap<>();
			for (FileDiffRegion region : regions) {
				newAnnotations.put(new ProjectionAnnotation(),
						new Position(region.getOffset(), region.getLength()));
			}
			viewer.getProjectionAnnotationModel().modifyAnnotations(
					currentFoldingAnnotations, newAnnotations, null);
			currentFoldingAnnotations = newAnnotations.keySet()
					.toArray(new Annotation[0]);
		} else {
			viewer.disableProjection();
			currentFoldingAnnotations = null;
		}
	}

	private void setOverviewAnnotations() {
		IDocumentProvider documentProvider = getDocumentProvider();
		IDocument document = documentProvider.getDocument(getEditorInput());
		if (!(document instanceof DiffDocument)) {
			return;
		}
		IAnnotationModel annotationModel = documentProvider
				.getAnnotationModel(getEditorInput());
		if (annotationModel == null) {
			return;
		}
		DiffRegion[] diffs = ((DiffDocument) document).getRegions();
		if (diffs == null || diffs.length == 0) {
			return;
		}
		Map<Annotation, Position> newAnnotations = new HashMap<>();
		for (DiffRegion region : diffs) {
			if (DiffRegion.Type.ADD.equals(region.getType())) {
				newAnnotations.put(
						new Annotation(ADD_ANNOTATION_TYPE, true, null),
						new Position(region.getOffset(), region.getLength()));
			} else if (DiffRegion.Type.REMOVE.equals(region.getType())) {
				newAnnotations.put(
						new Annotation(REMOVE_ANNOTATION_TYPE, true, null),
						new Position(region.getOffset(), region.getLength()));
			}
		}
		if (annotationModel instanceof IAnnotationModelExtension) {
			((IAnnotationModelExtension) annotationModel).replaceAnnotations(
					currentOverviewAnnotations, newAnnotations);
		} else {
			if (currentOverviewAnnotations != null) {
				for (Annotation existing : currentOverviewAnnotations) {
					annotationModel.removeAnnotation(existing);
				}
			}
			for (Map.Entry<Annotation, Position> entry : newAnnotations
					.entrySet()) {
				annotationModel.addAnnotation(entry.getKey(), entry.getValue());
			}
		}
		currentOverviewAnnotations = newAnnotations.keySet()
				.toArray(new Annotation[0]);
	}

	private FileDiffRegion getFileDiffRange(int widgetOffset) {
		DiffViewer viewer = (DiffViewer) getSourceViewer();
		if (viewer != null) {
			int offset = viewer.widgetOffset2ModelOffset(widgetOffset);
			IDocument document = getDocumentProvider()
					.getDocument(getEditorInput());
			if (document instanceof DiffDocument) {
				return ((DiffDocument) document).findFileRegion(offset);
			}
		}
		return null;
	}

	protected FileDiff[] getDiffs(IRepositoryCommit tip,
			IRepositoryCommit base) {
		List<FileDiff> diffResult = new ArrayList<>();

		if (base == null) {
			if (tip instanceof RepositoryCommit) {
				diffResult.addAll(asList(((RepositoryCommit) tip).getDiffs()));
				if (tip.getRevCommit().getParentCount() > 2) {
					RevCommit untrackedCommit = tip.getRevCommit()
							.getParent(StashEditorPage.PARENT_COMMIT_UNTRACKED);
					diffResult.addAll(
							asList(new RepositoryCommit(tip.getRepository(),
									untrackedCommit).getDiffs()));
				}
			}
		} else {
			Repository repository = tip.getRepository();
			RevCommit tipCommit = tip.getRevCommit();
			RevCommit baseCommit = base.getRevCommit();
			FileDiff[] diffsResult = null;
			try (RevWalk revWalk = new RevWalk(repository);
					TreeWalk treewalk = new TreeWalk(
							revWalk.getObjectReader())) {
				treewalk.setRecursive(true);
				treewalk.setFilter(TreeFilter.ANY_DIFF);
				revWalk.parseBody(tipCommit);
				revWalk.parseBody(baseCommit);
				diffsResult = FileDiff.compute(repository, treewalk, tipCommit,
						new RevCommit[] { baseCommit }, null, TreeFilter.ALL);
			} catch (IOException e) {
				diffsResult = new FileDiff[0];
			}
			return diffsResult;
		}

		FileDiff[] result = diffResult.toArray(new FileDiff[0]);
		Arrays.sort(result, FileDiff.PATH_COMPARATOR);
		return result;
	}

	private void formatDiff(@NonNull DiffEditorInput input) {
		IRepositoryCommit commit = input.getTip();
		if (commit instanceof RepositoryCommit) {
			RepositoryCommit repoCommit = (RepositoryCommit) commit;
			if (!repoCommit.isStash()
					&& repoCommit.getRevCommit().getParentCount() > 1) {
				input.setDocument(new Document());
				setInput(input);
				return;
			}
		}
		IRepositoryCommit base = input.getBase();
		Job job = new Job(UIText.DiffEditor_TaskGeneratingDiff) {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				FileDiff diffs[] = getDiffs(commit, base);
				DiffDocument document = new DiffDocument();
				try (DiffRegionFormatter formatter = new DiffRegionFormatter(
						document)) {
					SubMonitor progress = SubMonitor.convert(monitor,
							diffs.length);
					for (FileDiff diff : diffs) {
						if (progress.isCanceled()) {
							break;
						}
						progress.subTask(diff.getPath());
						try {
							formatter.write(diff);
						} catch (IOException ignore) {
						}
						progress.worked(1);
					}
					document.connect(formatter);
				}
				new UIJob(UIText.DiffEditor_TaskUpdatingViewer) {

					@Override
					public IStatus runInUIThread(IProgressMonitor uiMonitor) {
						if (UIUtils
								.isUsable(getSourceViewer().getTextWidget())) {
							input.setDocument(document);
							setInput(input);
						}
						return Status.OK_STATUS;
					}
				}.schedule();
				return Status.OK_STATUS;
			}

			@Override
			public boolean belongsTo(Object family) {
				return JobFamilies.DIFF == family || super.belongsTo(family);
			}
		};
		job.schedule();
	}

	private static class DiffDocumentProvider extends AbstractDocumentProvider {

		@Override
		public IStatus getStatus(Object element) {
			if (element instanceof DiffEditorInput) {
				DiffEditorInput input = (DiffEditorInput) element;
				if (input.getBase() == null) {
					IRepositoryCommit commit = input.getTip();
					if (commit.getRevCommit() != null
							&& commit.getRevCommit().getParentCount() > 1) {
						if (!(commit instanceof RepositoryCommit)
								|| !((RepositoryCommit) commit).isStash()) {
							return Activator.createErrorStatus(
									UIText.DiffEditor_WarningNoDiffForMerge);
						}
					}
				}
			}
			return Status.OK_STATUS;
		}

		@Override
		protected IDocument createDocument(Object element)
				throws CoreException {
			if (element instanceof DiffEditorInput) {
				IDocument document = ((DiffEditorInput) element).getDocument();
				if (document != null) {
					return document;
				}
			}
			return new Document();
		}

		@Override
		protected IAnnotationModel createAnnotationModel(Object element)
				throws CoreException {
			return new AnnotationModel();
		}

		@Override
		protected void doSaveDocument(IProgressMonitor monitor, Object element,
				IDocument document, boolean overwrite) throws CoreException {
		}

		@Override
		protected IRunnableContext getOperationRunner(
				IProgressMonitor monitor) {
			return null;
		}

	}

	private static class ThemePreferenceStore extends PreferenceStore {


		private static final String ADD_ANNOTATION_COLOR_PREFERENCE = "org.eclipse.egit.ui.commitEditor.diffAddedColor"; //$NON-NLS-1$

		private static final String REMOVE_ANNOTATION_COLOR_PREFERENCE = "org.eclipse.egit.ui.commitEditor.diffRemovedColor"; //$NON-NLS-1$

		private final IPropertyChangeListener listener = event -> {
			String property = event.getProperty();
			if (IThemeManager.CHANGE_CURRENT_THEME.equals(property)) {
				setColorRegistry();
				initColors();
			} else if (THEME_DiffAddBackgroundColor.equals(property)
					|| THEME_DiffRemoveBackgroundColor.equals(property)) {
				initColors();
			}
		};

		private ColorRegistry currentColors;

		public ThemePreferenceStore() {
			super();
			setColorRegistry();
			initColors();
			PlatformUI.getWorkbench().getThemeManager()
					.addPropertyChangeListener(listener);
		}

		private void setColorRegistry() {
			if (currentColors != null) {
				currentColors.removeListener(listener);
			}
			currentColors = PlatformUI.getWorkbench().getThemeManager()
					.getCurrentTheme().getColorRegistry();
			currentColors.addListener(listener);
		}

		private void initColors() {
			RGB rgb = adjust(currentColors.getRGB(THEME_DiffAddBackgroundColor),
					4.0);
			setValue(ADD_ANNOTATION_COLOR_PREFERENCE,
					StringConverter.asString(rgb));
			rgb = adjust(currentColors.getRGB(THEME_DiffRemoveBackgroundColor),
					4.0);
			setValue(REMOVE_ANNOTATION_COLOR_PREFERENCE,
					StringConverter.asString(rgb));
		}

		private RGB adjust(RGB rgb, double saturation) {
			float[] hsb = rgb.getHSB();
			return new RGB(hsb[0], (float) Math.min(hsb[1] * saturation, 1.0),
					hsb[2] < 0.5 ? hsb[2] * 2 : hsb[2]);
		}

		public void dispose() {
			PlatformUI.getWorkbench().getThemeManager()
					.removePropertyChangeListener(listener);
			if (currentColors != null) {
				currentColors.removeListener(listener);
				currentColors = null;
			}
		}
	}
}
