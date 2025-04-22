				};
			} else {
				};
			}
		} else {
			if (untrackedFiles) {
						"On branch master"
						"Untracked files:"
						""
						"\tstagedDeleted"
						"\tstagedModified"
						"\tstagedNew"
						"\ttracked"
						"\ttrackedDeleted"
						"\ttrackedModified"
						"\tuntracked"
				};
			} else {
						"On branch master"
				};
			}
		}

		assertArrayOfLinesEquals(output
	}

	private void assertStagedFiles(String command
			boolean untrackedFiles) throws Exception {
		String[] output = new String[0];

		if (porcelain) {
			if (untrackedFiles) {
