			try (InputStream fi = Files.newInputStream(archive.toPath());
					InputStream bi = new BufferedInputStream(fi);
					InputStream gzi = new GzipCompressorInputStream(bi);
					ArchiveInputStream o = new TarArchiveInputStream(gzi)) {
				assertEntries(o);
			}
