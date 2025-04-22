			addrs.add(new InetSocketAddress(hostPart,
					Integer.parseInt(portNum)));
		}
		assert !addrs.isEmpty() : "No addrs found";
		return addrs;
	}
