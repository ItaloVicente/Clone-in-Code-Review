				String upstreamCommitId = readFile(rebaseDir
				try {
					upstreamCommitName = readFile(rebaseDir
				} catch (FileNotFoundException e) {
					upstreamCommitName = upstreamCommitId;
				}
