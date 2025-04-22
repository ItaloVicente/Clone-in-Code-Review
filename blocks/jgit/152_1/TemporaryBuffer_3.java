			if (onDiskFile != null) {
				try {
					if (!onDiskFile.delete())
						onDiskFile.deleteOnExit();
				} finally {
					onDiskFile = null;
				}
