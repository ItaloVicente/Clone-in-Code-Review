					if (!pathMatch) {
						return true;
					} else {
						if (right == endExcl - 1) {
							return !dirOnly || assumeDirectory;
						}
						return wasWild;
					}
