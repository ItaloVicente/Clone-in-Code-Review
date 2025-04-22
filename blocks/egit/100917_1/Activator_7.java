				} else {
					IFile file = handled.get(containerPath);
					if (file != null) {
						handled.put(containerPath, null);
						result.remove(file);
						result.put(file.getParent(), Boolean.FALSE);
					}
