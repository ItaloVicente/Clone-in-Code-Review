		commentAndDiffScrolledComposite = new ScrolledComposite(revInfoSplit,
				SWT.H_SCROLL | SWT.V_SCROLL);
		commentAndDiffScrolledComposite.setExpandHorizontal(true);
		commentAndDiffScrolledComposite.setExpandVertical(true);

		commentAndDiffComposite = new Composite(commentAndDiffScrolledComposite, SWT.NONE);
		commentAndDiffScrolledComposite.setContent(commentAndDiffComposite);
		commentAndDiffComposite.setLayout(GridLayoutFactory.fillDefaults()
				.create());

		commentViewer = new CommitMessageViewer(commentAndDiffComposite,
				getPartSite());
		commentViewer.getControl().setLayoutData(
				GridDataFactory.fillDefaults().grab(true, false).create());

		commentViewer.addTextListener(new ITextListener() {
			@Override
			public void textChanged(TextEvent event) {
				resizeCommentAndDiffScrolledComposite();
			}
		});

		StyledText commentWidget = commentViewer.getTextWidget();
		commentWidget.addCaretListener(event -> {
			Point location = commentWidget
					.getLocationAtOffset(event.caretOffset);
			scrollCommentAndDiff(location,
					commentWidget.getLineHeight(event.caretOffset));
		});

		commentAndDiffComposite.setBackground(commentViewer.getControl()
				.getBackground());


		HyperlinkSourceViewer.Configuration configuration = new HyperlinkSourceViewer.Configuration(
				EditorsUI.getPreferenceStore()) {

			@Override
			public int getHyperlinkStateMask(ISourceViewer sourceViewer) {
				return SWT.NONE;
			}

			@Override
			protected IHyperlinkDetector[] internalGetHyperlinkDetectors(
					ISourceViewer sourceViewer) {
				IHyperlinkDetector[] registered = super.internalGetHyperlinkDetectors(
						sourceViewer);
				if (registered == null) {
					return new IHyperlinkDetector[] {
							new CommitMessageViewer.KnownHyperlinksDetector() };
				} else {
					IHyperlinkDetector[] result = new IHyperlinkDetector[registered.length
							+ 1];
					System.arraycopy(registered, 0, result, 0,
							registered.length);
					result[registered.length] = new CommitMessageViewer.KnownHyperlinksDetector();
					return result;
				}
			}

			@Override
			public String[] getConfiguredContentTypes(
					ISourceViewer sourceViewer) {
				return new String[] { IDocument.DEFAULT_CONTENT_TYPE,
						CommitMessageViewer.HEADER_CONTENT_TYPE,
						CommitMessageViewer.FOOTER_CONTENT_TYPE };
			}

			@Override
			public IPresentationReconciler getPresentationReconciler(
					ISourceViewer viewer) {
				PresentationReconciler reconciler = new PresentationReconciler();
				reconciler.setDocumentPartitioning(
						getConfiguredDocumentPartitioning(viewer));
				DefaultDamagerRepairer hyperlinkDamagerRepairer = new DefaultDamagerRepairer(
						new HyperlinkTokenScanner(this, viewer));
				reconciler.setDamager(hyperlinkDamagerRepairer,
						IDocument.DEFAULT_CONTENT_TYPE);
				reconciler.setRepairer(hyperlinkDamagerRepairer,
						IDocument.DEFAULT_CONTENT_TYPE);
				TextAttribute headerDefault = new TextAttribute(
						PlatformUI.getWorkbench().getDisplay()
								.getSystemColor(SWT.COLOR_DARK_GRAY));
				DefaultDamagerRepairer headerDamagerRepairer = new DefaultDamagerRepairer(
						new HyperlinkTokenScanner(this, viewer, headerDefault));
				reconciler.setDamager(headerDamagerRepairer,
						CommitMessageViewer.HEADER_CONTENT_TYPE);
				reconciler.setRepairer(headerDamagerRepairer,
						CommitMessageViewer.HEADER_CONTENT_TYPE);
				DefaultDamagerRepairer footerDamagerRepairer = new DefaultDamagerRepairer(
						new FooterTokenScanner(this, viewer));
				reconciler.setDamager(footerDamagerRepairer,
						CommitMessageViewer.FOOTER_CONTENT_TYPE);
				reconciler.setRepairer(footerDamagerRepairer,
						CommitMessageViewer.FOOTER_CONTENT_TYPE);
				return reconciler;
			}
