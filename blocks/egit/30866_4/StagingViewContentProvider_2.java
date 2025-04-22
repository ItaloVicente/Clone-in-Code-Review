					if (fileNameMode) {
						int result = e1.getName().compareTo(e2.getName());
						if (result != 0)
							return result;
						else
							return e1.getParentPath().toString()
									.compareTo(e2.getParentPath().toString());
					}
