				};
			}
		} else {
			if (untrackedFiles) {
						branchHeader
						"Unmerged paths:"
						""
						"\tboth modified:      unmerged"
						""
						"Untracked files:"
						""
						"\tuntracked"
				};
			} else {
						branchHeader
						"Unmerged paths:"
						""
						"\tboth modified:      unmerged"
				};
			}
		}

		assertArrayOfLinesEquals(output
