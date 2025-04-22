					.map(s -> {
						File f = new File(s);
						if (!f.isAbsolute()) {
							f = new File(sshDir, s);
						}
						return f.toPath();
					}).filter(Files::exists).collect(Collectors.toList());
