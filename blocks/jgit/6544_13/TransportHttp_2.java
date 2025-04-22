	class SmartHttpSubscribeConnection extends BasePackSubscribeConnection {
		SmartHttpSubscribeConnection() {
			super(TransportHttp.this);
			outNeedsEnd = false;
		}

		@Override
		public void sendSubscribeAdvertisement(Subscriber subscriber)
				throws IOException {
			Service svc = new LongPollService(SVC_PUBLISH_SUBSCRIBE);
			svc.setHandleAuth(true);
			start(svc.getInputStream()
			super.sendSubscribeAdvertisement(subscriber);
		}

		@Override
		public void subscribe(Subscriber subscriber
				Map<String
				PrintWriter output)
				throws InterruptedException

			Service svc = new LongPollService(SVC_PUBLISH_SUBSCRIBE);
			InputStream bufferedInput = new BufferedInputStream(svc
					.getInputStream()
			start(bufferedInput
			super.subscribe(subscriber
		}
	}

