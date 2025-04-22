				try (ZipFile zipFile = new ZipFile(filePath)) {
					fileName = zipFile.getName();
					Enumeration<? extends ZipEntry> entries = zipFile.entries();
					while (entries.hasMoreElements()) {
						ZipEntry entry = entries.nextElement();
						compressed = entry.getMethod() == ZipEntry.DEFLATED;
					}
				}
