		}
		ServerSocket socket = new ServerSocket();
		socket.setReuseAddress(true);
		if (myAddress != null) {
			socket.bind(myAddress
		} else {
			socket.bind(new InetSocketAddress((InetAddress) null
		}
		myAddress = (InetSocketAddress) socket.getLocalSocketAddress();
