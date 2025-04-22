				case COPY: {
					File dest = getFile(fh.getNewPath());
					if (!inCore()) {
						File src = getFile(fh.getOldPath());
						FileUtils.mkdirs(dest.getParentFile(), true);
						Files.copy(src.toPath(), dest.toPath());
					}
					apply(fh.getOldPath(), dirCache, dirCacheBuilder, dest, fh);
					break;
