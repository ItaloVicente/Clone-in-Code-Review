		LfsProtocolServlet protocol = new LfsProtocolServlet() {

			private static final long serialVersionUID = 1L;

			@Override
			protected LargeFileRepository getLargeFileRepository() {
				return repository;
			}

		};
