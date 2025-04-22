
				private Path resolve(@NonNull Path d) {
					File f = FileKey.resolve(d.toFile(), FS.DETECTED);
					if (f == null) {
						return null;
					}
					return f.toPath();
