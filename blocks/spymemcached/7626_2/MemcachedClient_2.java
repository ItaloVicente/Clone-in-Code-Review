		
		if (cf != null && !(cf instanceof BinaryConnectionFactory)) {
			throw new IllegalArgumentException("ConnectionFactory must be of type " +
					"BinaryConnectionFactory");
		}

		ConnectionFactoryBuilder cfb;
		if (cf == null) {
			cfb = new ConnectionFactoryBuilder();
		} else {
			cfb = new ConnectionFactoryBuilder(cf);
		}

