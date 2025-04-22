			headId = db.resolve(Constants.HEAD);
		} catch (IOException e) {
			throw new IllegalStateException(NLS.bind(
					UIText.GitHistoryPage_errorParsingHead, Activator
							.getDefault().getRepositoryUtil()
							.getRepositoryName(db)));
		}
		if (headId == null)
			throw new IllegalStateException(NLS.bind(
					UIText.GitHistoryPage_errorParsingHead, Activator
							.getDefault().getRepositoryUtil()
							.getRepositoryName(db)));

		List<String> paths = buildFilterPaths(input.getItems(), input
				.getFileList(), db);

		if (forceNewWalk || pathChange(pathFilters, paths)
				|| currentWalk == null || !headId.equals(currentHeadId)) {
			currentHeadId = headId;
			if (currentWalk != null)
				currentWalk.release();
			currentWalk = new SWTWalk(db);
			currentWalk.sort(RevSort.COMMIT_TIME_DESC, true);
			currentWalk.sort(RevSort.BOUNDARY, true);
		} else {
			currentWalk.reset();
		}
