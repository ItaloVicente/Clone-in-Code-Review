		execute(new Callable<Void>() {
			@Override
			public Void call() throws IOException {
				dst.write(buf, pos, cnt);
				return null;
			}
		});
