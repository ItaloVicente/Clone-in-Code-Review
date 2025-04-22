					java.nio.file.Path fp = file.toPath();
					if (Files.isSymbolicLink(fp)) {
						String sp = new String(getContent(), Constants.CHARSET);
						FileUtils.createSymLink(file, sp);
					} else {
						if (!file.exists()) {
							FileUtils.createNewFile(file);
						}
						try (FileOutputStream out = new FileOutputStream(
								file)) {
							out.write(getContent());
						}
