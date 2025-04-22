		return executor.submit(() -> {
			try {
				stream.write(data);
				return null;
			} finally {
				stream.close();
