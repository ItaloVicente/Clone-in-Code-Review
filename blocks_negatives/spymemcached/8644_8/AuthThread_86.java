			final OperationCallback cb=new OperationCallback() {
				public void receivedStatus(OperationStatus val) {
					if(val.getMessage().length() == 0) {
						done.set(true);
						node.authComplete();
						getLogger().info("Authenticated to "
								+ node.getSocketAddress());
					} else {
						foundStatus.set(val);
					}
				}
