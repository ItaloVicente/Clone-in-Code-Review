	class SmartHttpSubscribeConnection extends BasePackSubscribeConnection {
		SmartHttpSubscribeConnection() {
			super(TransportHttp.this);
			outNeedsEnd = false;
		}

		@Override
		public void doSubscribeAdvertisment(Subscriber subscriber)
				throws IOException {
			Service svc = new LongPollService(SVC_PUBLISH_SUBSCRIBE);
			svc.setHandleAuth(true);
			start(svc.getInputStream()
			super.doSubscribeAdvertisment(subscriber);
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

