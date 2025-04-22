				List<DiffEntry> dels = (List<DiffEntry>) o;
				long[] matrix = new long[dels.size() * adds.size()];
				int mNext = 0;
				for (int addIdx = 0; addIdx < adds.size(); addIdx++) {
					String addedName = adds.get(addIdx).newName;

					for (int delIdx = 0; delIdx < dels.size(); delIdx++) {
						String deletedName = dels.get(delIdx).oldName;

						int score = SimilarityRenameDetector.nameScore(addedName
						matrix[mNext] = SimilarityRenameDetector.encode(score
						mNext++;
					}
				}

				Arrays.sort(matrix);

				for (--mNext; mNext >= 0; mNext--) {
					long ent = matrix[mNext];
					int delIdx = SimilarityRenameDetector.srcFile(ent);
					int addIdx = SimilarityRenameDetector.dstFile(ent);
					DiffEntry d = dels.get(delIdx);
					DiffEntry a = adds.get(addIdx);

					if (a == null) {
						pm.update(1);
					}

					ChangeType type;
					if (d.changeType == ChangeType.DELETE) {
						d.changeType = ChangeType.RENAME;
						type = ChangeType.RENAME;
					} else {
						type = ChangeType.COPY;
					}

					entries.add(DiffEntry.pair(type
					adds.set(addIdx
					pm.update(1);
				}
