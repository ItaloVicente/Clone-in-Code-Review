			AccessEvent event = null;
			if (DispatcherType.REQUEST
					.equals(baseRequest.getDispatcherType())) {
				event = new AccessEvent((Request) request);
				synchronized (events) {
					events.add(event);
				}
			}

