	private static String getConflictText(StagingEntry entry) {
		if (entry.hasConflicts()) {
			StageState conflictType = entry.getConflictType();
			switch (conflictType) {
			case DELETED_BY_THEM:
				return UIText.StagingView_Conflict_MD_short;
			case DELETED_BY_US:
				return UIText.StagingView_Conflict_DM_short;
			case BOTH_MODIFIED:
				return UIText.StagingView_Conflict_M_short;
			case BOTH_ADDED:
				return UIText.StagingView_Conflict_A_short;
			default:
				break;
			}
		}
		return ""; //$NON-NLS-1$
	}

