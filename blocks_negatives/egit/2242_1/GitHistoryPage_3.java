			} else {
				pathFilters = null;
				currentWalk.setTreeFilter(TreeFilter.ALL);
				fileWalker.setFilter(TreeFilter.ANY_DIFF);
			}
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
			schedule(rj);
