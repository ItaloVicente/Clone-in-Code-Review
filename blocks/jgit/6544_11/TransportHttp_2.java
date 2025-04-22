	class SmartHttpSubscribeConnection extends BasePackSubscribeConnection {
		SmartHttpSubscribeConnection() {
			super(TransportHttp.this);
			outNeedsEnd = false;
		}

		@Override
		public void doSubscribeAdvertisement(Subscriber subscriber)
				throws IOException {
			Service svc = new LongPollService(SVC_PUBLISH_SUBSCRIBE);
			svc.setHandleAuth(true);
			start(svc.getInputStream()
			super.doSubscribeAdvertisement(subscriber);
		}

		@Override
		public void doSubscribe(Subscriber subscriber
				Map<String
				ProgressMonitor monitor)
				throws InterruptedException

			Service svc = new LongPollService(SVC_PUBLISH_SUBSCRIBE);
			InputStream bufferedInput = new BufferedInputStream(svc
					.getInputStream()
			start(bufferedInput
			super.doSubscribe(subscriber
		}
	}

