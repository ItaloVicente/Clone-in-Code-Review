					break;
				case RENAME: {
					File src = getFile(fh.getOldPath());
					File dest = getFile(fh.getNewPath());

					if (!inCore()) {
						/*
						 * this is odd: we rename the file on the FS, but
						 * apply() will write a fresh stream anyway, which will
						 * overwrite if there were hunks in the patch.
						 */
						try {
