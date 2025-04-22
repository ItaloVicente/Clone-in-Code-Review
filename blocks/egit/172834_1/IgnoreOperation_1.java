					int rscType = resource.getType();
					isDirectory = rscType != IResource.FILE;
					if (isDirectory) {
						IPath location = resource.getLocation();
						if (location != null) {
							isDirectory = !Files.isSymbolicLink(
									FileUtils.toPath(location.toFile()));
						}
					}
