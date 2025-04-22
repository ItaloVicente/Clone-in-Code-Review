						&& resource.hasConflicts()) {
					StageState conflictType = resource.getConflictType();
					if (conflictType == StageState.DELETED_BY_THEM) {
						overlay = MODIFY_DELETE_IMAGE;
					} else if (conflictType == StageState.DELETED_BY_US) {
						overlay = DELETE_MODIFY_IMAGE;
					} else {
						overlay = CONFLICT_IMAGE;
					}
				}
