		} finally {
			try {
				if (r != null)
					r.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
