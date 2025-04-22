				if (background) {
					if (gcLog == null) {
						return Collections.emptyList();
					}
					try {
						gcLog.write(e.getMessage());
						StringWriter sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						gcLog.write(sw.toString());
						gcLog.commit();
					} catch (IOException e2) {
						e2.addSuppressed(e);
						LOG.error(e2.getMessage(), e2);
					}
				} else {
					throw new JGitInternalException(e.getMessage(), e);
