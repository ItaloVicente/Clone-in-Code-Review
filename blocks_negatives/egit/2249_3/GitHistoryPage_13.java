			if (in.getFileList() != null) {
				count = in.getFileList().length;
				for (File file : in.getFileList()) {
					b.append(getRepoRelativePath(in.getRepository(), file));
					if (file.isDirectory())
						b.append('/');
					if (b.length() > 100) {
						break;
					}
					b.append(", "); //$NON-NLS-1$
				}
