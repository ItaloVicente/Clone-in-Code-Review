					if (checkPaths) {
						File testFile = new File(dirName);
						if (!FileKey.isGitRepository(testFile, FS.DETECTED))
							resultStrings.add(dirName);
					}
					resultStrings.add(dirName);
