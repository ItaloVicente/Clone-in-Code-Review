				};
			}
		} else {
			if (untrackedFiles) {
						branchHeader
						"Untracked files:"
						""
						"\tuntracked"
				};
			} else {
						branchHeader
				};
			}
		}

		assertArrayOfLinesEquals(output
	}

	private void assertUntrackedAndUnmerged(String command
			boolean untrackedFiles
		String[] output = new String[0];
				: "On branch " + branch;

		if (porcelain) {
			if (untrackedFiles) {
