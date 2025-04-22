				};
			} else {
						"On branch master"
						"Changes to be committed:"
						""
						"\tdeleted:    stagedDeleted"
						"\tmodified:   stagedModified"
						"\tnew file:   stagedNew"
						""
						"Changes not staged for commit:"
						""
						"\tdeleted:    trackedDeleted"
						"\tmodified:   trackedModified"
						""
				};
			}
		}

		assertArrayOfLinesEquals(output
	}

	private void assertUntracked(String command
			boolean porcelain
			boolean untrackedFiles
		String[] output = new String[0];
		String branchHeader = "On branch " + branch;

		if (porcelain) {
			if (untrackedFiles) {
