			try (InputStream fi = Files.newInputStream(archive.toPath());
					InputStream bi = new BufferedInputStream(fi);
					InputStream gzi = new BZip2CompressorInputStream(bi);
					ArchiveInputStream o = new TarArchiveInputStream(gzi)) {
				assertEntries(o);
			}
