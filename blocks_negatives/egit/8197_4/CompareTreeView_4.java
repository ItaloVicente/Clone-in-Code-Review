				if (compareVersionIterator != null
						&& baseVersionIterator != null) {
					monitor.setTaskName(baseVersionIterator
							.getEntryPathString());
					IPath currentPath = new Path(baseVersionIterator
							.getEntryPathString());
