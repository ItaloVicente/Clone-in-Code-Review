				if (p.exitValue() != 0) {
					logError(e);
					fail.set(true);
				} else {
				}
			} finally {
				if (err.length() > 0) {
					LOG.error(err.toString());
				}
