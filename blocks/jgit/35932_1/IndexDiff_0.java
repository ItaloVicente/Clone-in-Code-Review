
			for (int i = 0; i < treeWalk.getTreeCount(); i++) {
				Set<String> values = fileModes.get(treeWalk.getFileMode(i));
				if (values == null) {
					values = new HashSet<String>();
				}
				values.add(treeWalk.getPathString());
				fileModes.put(treeWalk.getFileMode(i)
			}
