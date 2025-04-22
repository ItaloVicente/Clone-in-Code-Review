			return new ForkLocalFetchConnection();

		UploadPackFactory<Void> upf = new UploadPackFactory<Void>() {
			@Override
			public UploadPack create(Void req
				return createUploadPack(db);
			}
		};
		return new InternalFetchConnection<Void>(this
