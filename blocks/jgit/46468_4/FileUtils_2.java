				try {
					if (!dst.delete()) {
						delete(dst
					}
					Files.move(src.toPath()
					return;
				} catch (IOException e2) {
				}
