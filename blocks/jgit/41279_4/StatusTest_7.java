				};
			} else {
						"A  stagedDeleted"
						"A  stagedModified"
						"A  tracked"
						"A  trackedDeleted"
						"A  trackedModified"
				};
			}
		} else {
			if (untrackedFiles) {
						"On branch master"
						"Changes to be committed:"
						""
						"\tnew file:   stagedDeleted"
						"\tnew file:   stagedModified"
						"\tnew file:   tracked"
						"\tnew file:   trackedDeleted"
						"\tnew file:   trackedModified"
						""
						"Untracked files:"
						""
						"\tstagedNew"
						"\tuntracked"
				};
			} else {
						"On branch master"
						"Changes to be committed:"
						""
						"\tnew file:   stagedDeleted"
						"\tnew file:   stagedModified"
						"\tnew file:   tracked"
						"\tnew file:   trackedDeleted"
						"\tnew file:   trackedModified"
				};
			}
		}

		assertArrayOfLinesEquals(output
	}

	private void assertAfterInitialCommit(String command
			boolean untrackedFiles) throws Exception {
		String[] output = new String[0];

		if (porcelain) {
			if (untrackedFiles) {
