					apply(fh.getNewPath(), dirCache, dirCacheBuilder, f, fh);
				}
					break;
				case MODIFY:
					apply(fh.getOldPath(), dirCache, dirCacheBuilder,
							getFile(fh.getOldPath()), fh);
