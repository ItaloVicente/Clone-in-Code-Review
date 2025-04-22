
		LfsProtocolServlet protocol = new LfsProtocolServlet() {
			private static final long serialVersionUID = 1L;

			@Override
			protected LargeFileRepository getLargeFileRepository(
					LfsRequest request
				return repository;
			}

			@Override
			protected LargeFileRepository getLargeFileRepository(
					LfsRequest request
					throws LfsException {
				return repository;
			}
		};
		app.addServlet(new ServletHolder(protocol)

