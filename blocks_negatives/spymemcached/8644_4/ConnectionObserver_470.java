	/**
	 * A connection has just successfully been established on the given socket.
	 *
	 * @param sa the address of the node whose connection was established
	 * @param reconnectCount the number of attempts before the connection was
	 *                       established
	 */
	void connectionEstablished(SocketAddress sa, int reconnectCount);

