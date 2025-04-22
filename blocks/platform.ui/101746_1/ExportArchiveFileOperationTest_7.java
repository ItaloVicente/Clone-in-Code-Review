				try (ZipFile zipFile = new ZipFile(filePath)) {
					Enumeration<? extends ZipEntry> entries = zipFile.entries();
					while (entries.hasMoreElements()) {
						ZipEntry entry = entries.nextElement();
						allEntries.add(entry.getName());
					}
				}
