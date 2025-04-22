						if (!res.containsKey(name)) {
							res.put(name, file);
						} else {
							IFile previousFile = res.get(name);
							if (previousFile.getFullPath().segmentCount() > file.getFullPath().segmentCount()) {
								res.put(file.getName(), file);
							}
						}
