				};
			} else {
				};
			}
		} else {
			if (untrackedFiles) {
						"On branch master"
						"Untracked files:"
						""
						"\tstagedNew"
						"\tuntracked"
				};
			} else {
						"On branch master"
				};
			}
		}

		assertArrayOfLinesEquals(output
	}

	private void assertStagedStatus(String command
			boolean untrackedFiles) throws Exception {
		String[] output = new String[0];

		if (porcelain) {
			if (untrackedFiles) {
