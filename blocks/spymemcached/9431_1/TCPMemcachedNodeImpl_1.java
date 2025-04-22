				synchronized(o) {
					assert o.getState() == OperationState.WRITING;
	
					ByteBuffer obuf=o.getBuffer();
					assert obuf != null : "Didn't get a write buffer from " + o;
					int bytesToCopy=Math.min(getWbuf().remaining(),
							obuf.remaining());
					byte b[]=new byte[bytesToCopy];
					obuf.get(b);
					getWbuf().put(b);
					getLogger().debug("After copying stuff from %s: %s",
							o, getWbuf());
					if(!o.getBuffer().hasRemaining()) {
						o.writeComplete();
						transitionWriteItem();
	
						preparePending();
						if(shouldOptimize) {
							optimize();
						}
	
						o=getNextWritableOp();
