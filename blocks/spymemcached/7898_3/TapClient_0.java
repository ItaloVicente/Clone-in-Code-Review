	public Operation tapDump(final String id) throws IOException, ConfigurationException {
		final TapConnectionProvider conn;
		if (vBucketAware) {
			conn = new TapConnectionProvider(baseList, bucketName, usr, pwd);
		} else {
			conn = new TapConnectionProvider(addrs);
		}

		final CountDownLatch latch=new CountDownLatch(1);
		final Operation op=conn.opFact.tapDump(id, new TapOperation.Callback() {

			public void receivedStatus(OperationStatus status) {
			}
			public void gotData(ResponseMessage tapMessage) {
				rqueue.add(tapMessage);
				messagesRead++;
			}
			public void gotAck(TapOpcode opcode, int opaque) {
				tapAck(conn, opcode, opaque, this);
			}
			public void complete() {
				latch.countDown();
			}});
		synchronized(omap) {
			omap.put(op, conn);
		}
		conn.addOp(op);
		return op;
	}

