		diffSection = createSection(parent, toolkit, span);
		diffSection.setText(UIText.CommitEditorPage_SectionFilesEmpty);
		Composite filesArea = createSectionClient(diffSection, toolkit);

		diffViewer = new CommitFileDiffViewer(filesArea, getSite(), SWT.MULTI
				| SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION
				| toolkit.getBorderStyle());
		diffViewer.getTable().setData(FormToolkit.KEY_DRAW_BORDER,
