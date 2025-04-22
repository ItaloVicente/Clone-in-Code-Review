				if (rsrc.getType() == IResource.FILE) {
					Long fileLength = null;
					try {
						fileLength = (Long) rsrc
								.getSessionProperty(FILE_LENGTH_KEY);
					} catch (CoreException e) {
					}
					if (fileLength != null) {
						length = fileLength.longValue();
					} else {
						length = asFile().length();
						try {
							rsrc.setSessionProperty(FILE_LENGTH_KEY, Long.valueOf(
									length));
						} catch (CoreException e) {
						}
					}
				} else {
