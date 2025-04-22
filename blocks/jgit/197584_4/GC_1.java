			potentialOrphanFiles = files.map(
							path -> path.getFileName().toString()).map(name -> {
						try {
							return new PackFile(packDir.toFile()
						} catch (IllegalArgumentException e) {
							return null;
						}
					}).filter(Objects::nonNull).filter(packFile -> {
						PackExt ext = packFile.getPackExt();
						if (packFile.getPackExt() == null) {
							return false;
						}
						if (PARENT_EXTS.contains(ext)) {
							seenPackFileIds.add(packFile.getId());
							return false;
						}
						return CHILD_EXTS.contains(ext);
					})
