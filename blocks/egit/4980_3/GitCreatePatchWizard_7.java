
			formatLabel = new Label(composite, SWT.NONE);
			formatLabel.setText(UIText.GitCreatePatchWizard_Format);

			formatCombo = new ComboViewer(composite, SWT.DROP_DOWN | SWT.READ_ONLY);
			formatCombo.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));
			formatCombo.setContentProvider(ArrayContentProvider.getInstance());
			formatCombo.setLabelProvider(new LabelProvider() {
				@Override
				public String getText(Object element) {
					return ((DiffHeaderFormat) element).getDescription();
				}
			});
			formatCombo.setInput(DiffHeaderFormat.values());
			formatCombo.setFilters(new ViewerFilter[] { new ViewerFilter() {
				@Override
				public boolean select(Viewer viewer, Object parentElement,
						Object element) {
					return commit != null
							|| !((DiffHeaderFormat) element).isCommitRequired();
				}
			}});
			formatCombo.setSelection(new StructuredSelection(DiffHeaderFormat.NONE));
