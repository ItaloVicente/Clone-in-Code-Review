						FileDiff[] diffs = FileDiff.compute(walker, commit);
						for (FileDiff diff : diffs) {
							currentEncoding = CompareUtils.
								getResourceEncoding(db, diff.getPath());
							diff.outputDiff(sb, db, diffFmt, isGit);
							diffFmt.flush();
						}
