				if (rsrc instanceof IFile) {
					File file = asFile();
					if (file != null)
						length = file.length();
					else
						length = 0;
				} else
