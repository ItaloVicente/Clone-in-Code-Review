				if (!f.canWrite()) {
					if (!f.setWritable(true)) {
						LOG.warn("File {} is read only
								f.getPath());
					}
				}
