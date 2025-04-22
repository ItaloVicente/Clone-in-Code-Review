
		if (cf != null && !(cf instanceof BinaryConnectionFactory)) {
			throw new IllegalArgumentException("ConnectionFactory must be of type " +
					"BinaryConnectionFactory");
		}

		ConnectionFactoryBuilder cfb = new ConnectionFactoryBuilder(cf);
