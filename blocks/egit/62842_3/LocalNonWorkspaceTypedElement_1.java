					java.nio.file.Path fp = file.toPath();
					if (Files.isSymbolicLink(fp)) {
						String sp = new String(getContent(), Constants.CHARSET)
								.trim();
						if (sp.indexOf('\n') > 0) {
							sp = sp.substring(0, sp.indexOf('\n')).trim();
						}
						if (!sp.isEmpty()) {
							FileUtils.createSymLink(file, sp);
						}
					} else {
						if (!file.exists()) {
							FileUtils.createNewFile(file);
						}
						try (FileOutputStream out = new FileOutputStream(
								file)) {
							out.write(getContent());
						}
