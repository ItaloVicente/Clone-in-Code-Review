		private static void write(Path p
			FileUtils.mkdirs(p.getParent().toFile()
			try (Writer w = new OutputStreamWriter(Files.newOutputStream(p)
					UTF_8)) {
				w.write(body);
			}
		}
