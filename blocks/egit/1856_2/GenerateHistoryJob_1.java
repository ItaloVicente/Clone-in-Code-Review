			final SWTCommit[] asArray = new SWTCommit[allCommits.size()];
			allCommits.toArray(asArray);
			page.showCommitList(this, allCommits, asArray);
			lastUpdateCnt = allCommits.size();
		} finally {
			if (trace)
				GitTraceLocation.getTrace().traceExit(
						GitTraceLocation.HISTORYVIEW.getLocation());
		}
