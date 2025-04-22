					existingBuilder.add(dcEntry);
					tempBuilder.add(dcEntry);

					if (emptyCommit
							&& (hTree == null || !hTree.idEqual(fTree) || hTree
									.getEntryRawMode() != fTree
									.getEntryRawMode()))
						emptyCommit = false;
