				String pathWithOriginalContent = inCore() ?
						fh.getOldPath() : fh.getNewPath();
				apply(pathWithOriginalContent
				break;
			}
			case COPY: {
				File dest = getFile(fh.getNewPath());
				if (!inCore()) {
					File src = getFile(fh.getOldPath());
					FileUtils.mkdirs(dest.getParentFile()
					Files.copy(src.toPath()
