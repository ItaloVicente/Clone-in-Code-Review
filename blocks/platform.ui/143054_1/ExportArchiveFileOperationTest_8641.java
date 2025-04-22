				while (entries.hasMoreElements()){
					TarEntry entry = (TarEntry)entries.nextElement();
					allEntries.add(entry.getName());
				}
				tarFile.close();
			}
			if (flattenPaths) {
