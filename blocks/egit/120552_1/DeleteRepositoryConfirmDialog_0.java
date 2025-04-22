									Integer.valueOf(projectsToDelete.size())));
			projectsViewer = new TableViewer(main, SWT.BORDER | SWT.V_SCROLL);
			GridDataFactory.fillDefaults().grab(true, true).indent(20, 0)
					.hint(SWT.DEFAULT, 100)
					.applyTo(projectsViewer.getControl());

			projectsViewer.setLabelProvider(WorkbenchLabelProvider
					.getDecoratingWorkbenchLabelProvider());
			projectsViewer.setContentProvider(ArrayContentProvider.getInstance());
			projectsViewer.setInput(projectsToDelete);
			projectsViewer.getControl().setEnabled(false);
