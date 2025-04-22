		return executor.submit(new Callable<Object>() {
			@Override
			public Object call() throws IOException {
				try {
					stream.write(data);
					return null;
				} finally {
					stream.close();
				}
			}
		});
