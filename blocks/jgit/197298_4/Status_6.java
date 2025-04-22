			if (state != StageState.BOTH_DELETED) {
				if (state != StageState.DELETED_BY_US &&
						state != StageState.DELETED_BY_THEM) {
					printHint(CLIText.get().hintsUnmergedBothDeletedAdd
							true
				} else {
					printHint(CLIText.get().hintsUnmergedBothDeletedAddRm
							true
				}
			} else if (state == StageState.ADDED_BY_US ||
					state == StageState.ADDED_BY_THEM ||
					state == StageState.BOTH_ADDED ||
					state == StageState.BOTH_MODIFIED) {
				printHint(CLIText.get().hintsUnmergedBothDeletedRm
						true
			} else {
				printHint(CLIText.get().hintsUnmergedBothDeletedAddRm
						true
			}
