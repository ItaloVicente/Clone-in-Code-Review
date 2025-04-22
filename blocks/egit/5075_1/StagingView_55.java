				boolean submoduleSelected = false;
				for (Object item : selection.toArray())
					if (((StagingEntry) item).isSubmodule()) {
						submoduleSelected = true;
						break;
					}

				Action openWorkingTreeVersion = new Action(
