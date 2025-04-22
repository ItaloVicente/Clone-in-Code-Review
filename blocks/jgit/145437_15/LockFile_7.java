			try {
				Files.setLastModifiedTime(lck.toPath()
						FileTime.from(Instant.now()));
			} catch (IOException e) {
				n.waitUntilNotRacy();
			}
