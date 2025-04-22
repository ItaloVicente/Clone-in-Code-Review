			potentialOrphanFiles = files.map(path -> {
						try {
							return new PackFile(path.toFile());
						} catch (IllegalArgumentException e) {
							return null;
						}
					}).filter(Objects::nonNull).filter(packFile -> {
						PackExt ext = packFile.getPackExt();
						if (ext == null) {
							return false;
						}
						if (PARENT_EXTS.contains(ext)) {
							seenPackFileIds.add(packFile.getId());
							return false;
						}
						return CHILD_EXTS.contains(ext);
					})
