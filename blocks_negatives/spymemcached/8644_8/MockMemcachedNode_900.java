	private final InetSocketAddress socketAddress;
	public SocketAddress getSocketAddress() {return socketAddress;}

	public MockMemcachedNode(InetSocketAddress socketAddress) {
		this.socketAddress = socketAddress;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MockMemcachedNode that = (MockMemcachedNode) o;

		if (socketAddress != null
				? !socketAddress.equals(that.socketAddress)
				: that.socketAddress != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return (socketAddress != null ? socketAddress.hashCode() : 0);
	}

	public void copyInputQueue() {
	}
	public void setupResend() {
	}
	public void fillWriteBuffer(boolean optimizeGets) {
	}
	public void transitionWriteItem() {
	}
	public Operation getCurrentReadOp() {return null;}
	public Operation removeCurrentReadOp() {return null;}
	public Operation getCurrentWriteOp() {return null;}
	public Operation removeCurrentWriteOp() {return null;}
	public boolean hasReadOp() {return false;}
	public boolean hasWriteOp() {return false;}
	public void addOp(Operation op) {
	}
	public void insertOp(Operation op) {
	}
	public int getSelectionOps() {return 0;}
	public ByteBuffer getRbuf() {return null;}
	public ByteBuffer getWbuf() {return null;}
	public boolean isActive() {return false;}
	public void reconnecting() {
	}
	public void connected() {
	}
	public int getReconnectCount() {return 0;}
	public void registerChannel(SocketChannel ch, SelectionKey selectionKey) {
	}
	public void setChannel(SocketChannel to) {
	}
	public SocketChannel getChannel() {return null;}
	public void setSk(SelectionKey to) {
	}
	public SelectionKey getSk() {return null;}
	public int getBytesRemainingToWrite() {return 0;}
	public int writeSome() throws IOException {return 0;}
	public void fixupOps() {
	}

	public Collection<Operation> destroyInputQueue() {
		return null;
	}

	public void authComplete() {
	}

	public void setupForAuth() {
	}

	public int getContinuousTimeout() {
		return 0;
	}

	public void setContinuousTimeout(boolean timedOut) {
	}
}
