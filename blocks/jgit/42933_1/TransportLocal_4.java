			return new ForkLocalPushConnection();

		ReceivePackFactory<Void> rpf = new ReceivePackFactory<Void>() {
			@Override
			public ReceivePack create(Void req
				return createReceivePack(db);
			}
		};
		return new InternalPushConnection<Void>(this
