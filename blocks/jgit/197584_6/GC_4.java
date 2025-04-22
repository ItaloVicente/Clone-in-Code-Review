			potentialOrphanFiles = files.map(GC::toPackFileWithValidExt)
					.filter(Optional::isPresent)
					.map(Optional::get)
					.filter(packFile -> {
						PackExt ext = packFile.getPackExt();
						if (PARENT_EXTS.contains(ext)) {
							seenPackFileIds.add(packFile.getId());
							return false;
						}
						return CHILD_EXTS.contains(ext);
					})
