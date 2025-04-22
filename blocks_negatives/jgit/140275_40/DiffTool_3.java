				if (launchCompare) {
					try {
						FileElement local = createFileElement(
								FileElement.Type.LOCAL, sourcePair, Side.OLD,
								ent);
						FileElement remote = createFileElement(
								FileElement.Type.REMOTE, sourcePair, Side.NEW,
								ent);
						FileElement merged = new FileElement(mergedFilePath,
								FileElement.Type.MERGED);
