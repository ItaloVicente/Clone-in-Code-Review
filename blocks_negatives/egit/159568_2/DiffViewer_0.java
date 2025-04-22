					if (side == DiffEntry.Side.NEW) {
						File file = new Path(fileRange.getDiff().getRepository()
								.getWorkTree().getAbsolutePath()).append(
										fileRange.getDiff().getNewPath())
										.toFile();
						if (file.exists()) {
							links.add(new FileLink(linkRegion, file, -1));
						}
