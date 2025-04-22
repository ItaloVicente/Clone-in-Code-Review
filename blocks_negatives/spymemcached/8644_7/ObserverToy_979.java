public class ObserverToy {
	public static void main(String args[]) throws Exception {
		final ConnectionObserver obs = new ConnectionObserver() {
			public void connectionEstablished(SocketAddress sa,
					int reconnectCount) {
				System.out.println("*** Established:  " + sa
						+ " count=" + reconnectCount);
			}

			public void connectionLost(SocketAddress sa) {
				System.out.println("*** Lost connection:  " + sa);
			}

		};

		MemcachedClient c = new MemcachedClient(
			new DefaultConnectionFactory() {

				@Override
				public Collection<ConnectionObserver> getInitialObservers() {
					return Collections.singleton(obs);
				}

				@Override
				public boolean isDaemon() {
					return false;
				}

			},
			AddrUtil.getAddresses("localhost:11212"));

		while(true) {
			c.waitForQueues(1, TimeUnit.SECONDS);
			Thread.sleep(1000);
		}
	}
