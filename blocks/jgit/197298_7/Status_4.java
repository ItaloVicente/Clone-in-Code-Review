			if (!porcelain) {
				if (state != StageState.BOTH_DELETED) {
					if (state != StageState.DELETED_BY_US &&
							state != StageState.DELETED_BY_THEM) {
						printHint(CLIText.get().hintsUnmergedBothDeletedAdd);
					} else {
						printHint(CLIText.get().hintsUnmergedBothDeletedAddRm);
					}
				} else if (state == StageState.ADDED_BY_US ||
						state == StageState.ADDED_BY_THEM ||
						state == StageState.BOTH_ADDED ||
						state == StageState.BOTH_MODIFIED) {
					printHint(CLIText.get().hintsUnmergedBothDeletedRm);
				} else {
					printHint(CLIText.get().hintsUnmergedBothDeletedAddRm);
				}
			}
