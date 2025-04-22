							File file = new Path(
									fileDiff.getDiff().getRepository()
											.getWorkTree().getAbsolutePath())
													.append(fileDiff.getDiff()
															.getNewPath())
													.toFile();
