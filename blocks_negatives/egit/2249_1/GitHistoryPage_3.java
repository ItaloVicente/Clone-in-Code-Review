			setWalkStartPoints(db, headId);

			final TreeWalk fileWalker = setupFileViewer(db, paths);
			setupCommentViewer(db, fileWalker);

			scheduleNewGenerateHistoryJob();
		} finally {
			if (trace)
				GitTraceLocation.getTrace().traceExit(
						GitTraceLocation.HISTORYVIEW.getLocation());

		}
