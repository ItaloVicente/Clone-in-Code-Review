
	/* (non-Javadoc)
	 * @see net.spy.memcached.MemcachedNode#insertOp(net.spy.memcached.ops.Operation)
	 */
	public final void insertOp(Operation op) {
		ArrayList<Operation> tmp = new ArrayList<Operation>(
				inputQueue.size() + 1);
		tmp.add(op);
		inputQueue.drainTo(tmp);
		inputQueue.addAll(tmp);
	}

	/* (non-Javadoc)
	 * @see net.spy.memcached.MemcachedNode#getSelectionOps()
	 */
	public final int getSelectionOps() {
		int rv=0;
		if(getChannel().isConnected()) {
			if(hasReadOp()) {
				rv |= SelectionKey.OP_READ;
			}
			if(toWrite > 0 || hasWriteOp()) {
				rv |= SelectionKey.OP_WRITE;
			}
		} else {
			rv = SelectionKey.OP_CONNECT;
		}
		return rv;
	}

	/* (non-Javadoc)
	 * @see net.spy.memcached.MemcachedNode#getRbuf()
	 */
	public final ByteBuffer getRbuf() {
		return rbuf;
	}

	/* (non-Javadoc)
	 * @see net.spy.memcached.MemcachedNode#getWbuf()
	 */
	public final ByteBuffer getWbuf() {
		return wbuf;
	}

	/* (non-Javadoc)
	 * @see net.spy.memcached.MemcachedNode#getSocketAddress()
	 */
	public final SocketAddress getSocketAddress() {
		return socketAddress;
	}

	/* (non-Javadoc)
	 * @see net.spy.memcached.MemcachedNode#isActive()
	 */
	public final boolean isActive() {
		return reconnectAttempt == 0
			&& getChannel() != null && getChannel().isConnected();
	}

	/* (non-Javadoc)
	 * @see net.spy.memcached.MemcachedNode#reconnecting()
	 */
	public final void reconnecting() {
		reconnectAttempt++;
		continuousTimeout.set(0);
	}

	/* (non-Javadoc)
	 * @see net.spy.memcached.MemcachedNode#connected()
	 */
	public final void connected() {
		reconnectAttempt=0;
		continuousTimeout.set(0);
	}

	/* (non-Javadoc)
	 * @see net.spy.memcached.MemcachedNode#getReconnectCount()
	 */
	public final int getReconnectCount() {
		return reconnectAttempt;
	}

	/* (non-Javadoc)
	 * @see net.spy.memcached.MemcachedNode#toString()
	 */
	@Override
	public final String toString() {
		int sops=0;
		if(getSk()!= null && getSk().isValid()) {
			sops=getSk().interestOps();
		}
		int rsize=readQ.size() + (optimizedOp == null ? 0 : 1);
		int wsize=writeQ.size();
		int isize=inputQueue.size();
		return "{QA sa=" + getSocketAddress() + ", #Rops=" + rsize
			+ ", #Wops=" + wsize
			+ ", #iq=" + isize
			+ ", topRop=" + getCurrentReadOp()
			+ ", topWop=" + getCurrentWriteOp()
			+ ", toWrite=" + toWrite
			+ ", interested=" + sops + "}";
	}

	/* (non-Javadoc)
	 * @see net.spy.memcached.MemcachedNode#registerChannel(java.nio.channels.SocketChannel, java.nio.channels.SelectionKey)
	 */
	public final void registerChannel(SocketChannel ch, SelectionKey skey) {
		setChannel(ch);
		setSk(skey);
	}

	/* (non-Javadoc)
	 * @see net.spy.memcached.MemcachedNode#setChannel(java.nio.channels.SocketChannel)
	 */
	public final void setChannel(SocketChannel to) {
		assert channel == null || !channel.isOpen()
			: "Attempting to overwrite channel";
		channel = to;
	}

	/* (non-Javadoc)
	 * @see net.spy.memcached.MemcachedNode#getChannel()
	 */
	public final SocketChannel getChannel() {
		return channel;
	}

	/* (non-Javadoc)
	 * @see net.spy.memcached.MemcachedNode#setSk(java.nio.channels.SelectionKey)
	 */
	public final void setSk(SelectionKey to) {
		sk = to;
	}

	/* (non-Javadoc)
	 * @see net.spy.memcached.MemcachedNode#getSk()
	 */
	public final SelectionKey getSk() {
		return sk;
	}

	/* (non-Javadoc)
	 * @see net.spy.memcached.MemcachedNode#getBytesRemainingInBuffer()
	 */
	public final int getBytesRemainingToWrite() {
		return toWrite;
	}

	/* (non-Javadoc)
	 * @see net.spy.memcached.MemcachedNode#writeSome()
	 */
	public final int writeSome() throws IOException {
		int wrote=channel.write(wbuf);
		assert wrote >= 0 : "Wrote negative bytes?";
		toWrite -= wrote;
		assert toWrite >= 0
			: "toWrite went negative after writing " + wrote
				+ " bytes for " + this;
		getLogger().debug("Wrote %d bytes", wrote);
		return wrote;
	}


	/* (non-Javadoc)
	 * @see net.spy.memcached.MemcachedNode#setContinuousTimeout
	 */
	public void setContinuousTimeout(boolean timedOut) {
		if (timedOut && isActive()) {
			continuousTimeout.incrementAndGet();
		} else {
			continuousTimeout.set(0);
		}
	}

	/* (non-Javadoc)
	 * @see net.spy.memcached.MemcachedNode#getContinuousTimeout
	 */
	public int getContinuousTimeout() {
		return continuousTimeout.get();
	}


	public final void fixupOps() {
		SelectionKey s = sk;
		if(s != null && s.isValid()) {
			int iops=getSelectionOps();
			getLogger().debug("Setting interested opts to %d", iops);
			s.interestOps(iops);
		} else {
			getLogger().debug("Selection key is not valid.");
		}
	}

	public final void authComplete() {
		if (reconnectBlocked != null && reconnectBlocked.size() > 0 ) {
		    inputQueue.addAll(reconnectBlocked);
		}
		authLatch.countDown();
	}

	public final void setupForAuth() {
		if (shouldAuth) {
			authLatch = new CountDownLatch(1);
			if (inputQueue.size() > 0) {
				reconnectBlocked = new ArrayList<Operation>(
				inputQueue.size() + 1);
				inputQueue.drainTo(reconnectBlocked);
			}
			assert(inputQueue.size() == 0);
			setupResend();
		} else {
			authLatch = new CountDownLatch(0);
		}
	}

