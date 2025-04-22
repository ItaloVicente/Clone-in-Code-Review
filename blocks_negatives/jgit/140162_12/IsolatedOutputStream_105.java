			close = copier.submit(new Callable<Void>() {
				@Override
				public Void call() throws IOException {
					dst.close();
					return null;
				}
