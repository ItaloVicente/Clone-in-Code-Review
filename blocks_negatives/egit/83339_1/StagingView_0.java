						StagingEntry entry = (StagingEntry) selected;
						File file = new File(
								entry.getRepository().getWorkTree(),
								entry.getPath());
						if (file.exists())
							files.add(file.getAbsolutePath());
