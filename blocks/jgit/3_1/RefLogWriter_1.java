			final FileOutputStream out = new FileOutputStream(reflog
			try {
				out.write(rec);
			} finally {
				out.close();
			}
