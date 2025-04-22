
		commentAndDiffScrolledComposite = new ScrolledComposite(revInfoSplit,
				SWT.H_SCROLL | SWT.V_SCROLL);
		commentAndDiffScrolledComposite.setExpandHorizontal(true);
		commentAndDiffScrolledComposite.setExpandVertical(true);

		commentAndDiffComposite = new Composite(commentAndDiffScrolledComposite, SWT.NONE);
		commentAndDiffScrolledComposite.setContent(commentAndDiffComposite);
		commentAndDiffComposite.setLayout(GridLayoutFactory.fillDefaults()
				.create());

		commentViewer = new CommitMessageViewer(commentAndDiffComposite,
				getSite(), getPartSite());
		commentViewer.getControl().setLayoutData(
				GridDataFactory.fillDefaults().grab(true, false).create());

		commentViewer.addTextListener(new ITextListener() {
			public void textChanged(TextEvent event) {
				resizeCommentAndDiffScrolledComposite();
			}
		});

		commentAndDiffComposite.setBackground(commentViewer.getControl()
				.getBackground());

