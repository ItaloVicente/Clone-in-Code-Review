
	private void printUnmerged(Map<String
			throws IOException {
		List<String> paths = new ArrayList<String>(unmergedStates.keySet());
		Collections.sort(paths);
		for (String path : paths) {
			StageState state = unmergedStates.get(path);
			String stateDescription = getStageStateDescription(state);
			outw.println(CLIText.formatLine(String.format(
					statusFileListFormatUnmerged
			outw.flush();
		}
	}

	private static String getStageStateDescription(StageState stageState) {
		CLIText text = CLIText.get();
		switch (stageState) {
		case BOTH_DELETED:
			return text.statusBothDeleted;
		case ADDED_BY_US:
			return text.statusAddedByUs;
		case DELETED_BY_THEM:
			return text.statusDeletedByThem;
		case ADDED_BY_THEM:
			return text.statusAddedByThem;
		case DELETED_BY_US:
			return text.statusDeletedByUs;
		case BOTH_ADDED:
			return text.statusBothAdded;
		case BOTH_MODIFIED:
			return text.statusBothModified;
		default:
					+ stageState);
		}
	}
