					if (!containerPattern.startsWith(Character.toString('*'))) {
						if (!containerPattern.startsWith(Character.toString(IPath.SEPARATOR))) {
							containerPattern = IPath.SEPARATOR + containerPattern;
						}
						containerPattern = '*' + containerPattern;
					}
