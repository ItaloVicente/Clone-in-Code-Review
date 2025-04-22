		} catch (java.util.concurrent.TimeoutException timeoutException) {
		        debugNodeInfo(client.getNodeLocator().getAll());
			client.asyncGet("boundaryAfter").get(30, TimeUnit.SECONDS);
			throw new Exception("Unexpected timeout after 30 seconds waiting", timeoutException);
		}
	}

	private void debugNodeInfo(Collection<MemcachedNode> nodes) {
	    System.err.println("Debug nodes:");
	    for (MemcachedNode node : nodes) {
		    System.err.println(node);
		    System.err.println("Is active? " + node.isActive());
		    System.err.println("Has read operation? " + node.hasReadOp() + " Has write operation? " + node.hasWriteOp());
		try {
		    System.err.println("Has timed out this many times: " + node.getContinuousTimeout());
		    System.err.println("Write op: " + node.getCurrentWriteOp());
		    System.err.println("Read op: " + node.getCurrentReadOp());
		} catch (UnsupportedOperationException e) {
		    System.err.println("Node does not support full interface, likely read only.");
		}
	    }
