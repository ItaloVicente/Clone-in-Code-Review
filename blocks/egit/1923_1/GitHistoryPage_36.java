			fileViewer.setTreeWalk(db, fileWalker);
			fileViewer.refresh();
			fileViewer.addSelectionChangedListener(commentViewer);
			commentViewer.setTreeWalk(fileWalker);
			commentViewer.setDb(db);
			commentViewer.refresh();

			final SWTCommitList list;
			list = new SWTCommitList(graph.getControl().getDisplay());
			list.source(currentWalk);
			final GenerateHistoryJob rj = new GenerateHistoryJob(this, list);
			rj.addJobChangeListener(new JobChangeAdapter() {
				@Override
				public void done(final IJobChangeEvent event) {
					final Control graphctl = graph.getControl();
					if (job != rj || graphctl.isDisposed())
						return;
					graphctl.getDisplay().asyncExec(new Runnable() {
						public void run() {
							if (job == rj)
								job = null;
						}
					});
				}
			});
			job = rj;
			if (trace)
				GitTraceLocation.getTrace().trace(
						GitTraceLocation.HISTORYVIEW.getLocation(),
						"Scheduling GenerateHistoryJob"); //$NON-NLS-1$
			schedule(rj);
		} finally {
			if (trace)
				GitTraceLocation.getTrace().traceExit(
						GitTraceLocation.HISTORYVIEW.getLocation());

		}
