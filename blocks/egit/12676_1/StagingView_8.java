				for (Object item : selection.toArray()) {
					if (item instanceof StagingFolderEntry) {
						manager.removeAll();
						return;
					} else {
						if (((StagingEntry) item).isSubmodule()) {
							submoduleSelected = true;
							break;
						}
