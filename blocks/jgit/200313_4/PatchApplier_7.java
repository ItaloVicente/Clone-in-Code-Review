				break;
			case RENAME: {
				File src = getFile(fh.getOldPath());
				File dest = getFile(fh.getNewPath());

				if (!inCore()) {
					FileUtils.mkdirs(dest.getParentFile()
					FileUtils.rename(src
							StandardCopyOption.ATOMIC_MOVE);
