

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		Control[] children = parent.getChildren();
		textControl = children[children.length - 1];
	}


	@Override
	public ShowInContext getShowInContext() {
		RepositoryCommit commit = Adapters.adapt(getEditorInput(),
				RepositoryCommit.class);
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

	/**
	 * Gets the full unified diff of a {@link RepositoryCommit}.
	 *
	 * @param commit
	 *            to get the diff
	 * @return the diff as a sorted (by file path) array of {@link FileDiff}s
	 */
	protected FileDiff[] getDiffs(RepositoryCommit commit) {
		List<FileDiff> diffResult = new ArrayList<>();

		diffResult.addAll(asList(commit.getDiffs()));

		if (commit.getRevCommit().getParentCount() > 2) {
			RevCommit untrackedCommit = commit.getRevCommit()
					.getParent(StashEditorPage.PARENT_COMMIT_UNTRACKED);
			diffResult
					.addAll(asList(new RepositoryCommit(commit.getRepository(),
							untrackedCommit).getDiffs()));
		}
		FileDiff[] result = diffResult.toArray(new FileDiff[0]);
		Arrays.sort(result, FileDiff.PATH_COMPARATOR);
		return result;
	}

	/**
	 * Asynchronously gets the diff of the commit set on our
	 * {@link CommitEditorInput}, formats it into a {@link DiffDocument}, and
	 * then re-sets this editors's input to a {@link DiffEditorInput} which will
	 * cause this document to be shown.
	 */
	private void formatDiff() {
		RepositoryCommit commit = Adapters.adapt(getEditor(),
				RepositoryCommit.class);
		if (commit == null) {
			return;
		}
		if (!commit.isStash() && commit.getRevCommit().getParentCount() > 1) {
			setInput(new DiffEditorInput(commit, null));
			return;
		}

		Job job = new Job(UIText.DiffEditorPage_TaskGeneratingDiff) {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				FileDiff diffs[] = getDiffs(commit);
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
				new UIJob(UIText.DiffEditorPage_TaskUpdatingViewer) {

					@Override
					public IStatus runInUIThread(IProgressMonitor uiMonitor) {
						if (UIUtils.isUsable(getPartControl())) {
							setInput(new DiffEditorInput(commit, document));
						}
						return Status.OK_STATUS;
					}
				}.schedule();
				return Status.OK_STATUS;
			}
		};
		job.schedule();
	}

	/**
	 * An editor input that gives access to the document created by the diff
	 * formatter.
	 */
	private static class DiffEditorInput extends CommitEditorInput {

		private IDocument document;

		public DiffEditorInput(RepositoryCommit commit, IDocument diff) {
			super(commit);
			document = diff;
		}

		public IDocument getDocument() {
			return document;
		}

		@Override
		public String getName() {
			return UIText.DiffEditorPage_Title;
		}

		@Override
		public boolean equals(Object obj) {
			return super.equals(obj) && (obj instanceof DiffEditorInput)
					&& Objects.equals(document,
							((DiffEditorInput) obj).document);
		}

		@Override
		public int hashCode() {
			return super.hashCode() ^ Objects.hashCode(document);
		}
	}

	/**
	 * A document provider that knows about {@link DiffEditorInput}.
	 */
	private static class DiffDocumentProvider extends AbstractDocumentProvider {

		@Override
		public IStatus getStatus(Object element) {
			if (element instanceof CommitEditorInput) {
				RepositoryCommit commit = ((CommitEditorInput) element)
						.getCommit();
				if (commit != null && !commit.isStash()
						&& commit.getRevCommit() != null
						&& commit.getRevCommit().getParentCount() > 1) {
					return Activator.createErrorStatus(
							UIText.DiffEditorPage_WarningNoDiffForMerge);
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

	/**
	 * An ephemeral {@link PreferenceStore} that sets the annotation colors
	 * based on the theme-dependent colors defined for the line backgrounds in a
	 * unified diff. This ensures that the annotation colors are always
	 * consistent with the line backgrounds. Plus the user doesn't have to
	 * configure several related colors, and the annotations update when the
	 * theme changes.
	 */
	private static class ThemePreferenceStore extends PreferenceStore {




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

		/**
		 * Increases the saturation (simple multiplier), and brightens dark
		 * colors.
		 *
		 * @param rgb
		 *            to modify
		 * @param saturation
		 *            multiplier
		 * @return A new {@link RGB} for the new saturated and possibly
		 *         brightened color
		 */
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
