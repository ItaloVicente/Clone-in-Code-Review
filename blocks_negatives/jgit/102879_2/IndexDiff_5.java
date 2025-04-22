				for (int i = 0; i < treeWalk.getTreeCount(); i++) {
					Set<String> values = fileModes.get(treeWalk.getFileMode(i));
					String path = treeWalk.getPathString();
					if (path != null) {
						if (values == null)
							values = new HashSet<>();
						values.add(path);
						fileModes.put(treeWalk.getFileMode(i), values);
