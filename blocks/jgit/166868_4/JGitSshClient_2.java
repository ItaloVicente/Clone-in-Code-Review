		int originalPort = hostConfig.getPort();
		ValidateUtils.checkTrue(originalPort > 0
				originalPort);
		InetSocketAddress originalAddress = new InetSocketAddress(originalHost
				originalPort);
		InetSocketAddress targetAddress = originalAddress;
