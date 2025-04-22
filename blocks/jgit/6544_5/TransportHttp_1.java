	class SmartHttpSubscribeConnection extends BasePackSubscribeConnection {
		SmartHttpSubscribeConnection(InputStream advertisement) {
			super(TransportHttp.this);
			init(advertisement
			outNeedsEnd = false;
		}

		@Override
		public void doSubscribe(Subscriber subscriber
				Map<String
				ProgressMonitor monitor)
				throws InterruptedException

			Service svc = new LongPollService(SVC_PUBLISH_SUBSCRIBE);
			InputStream bufferedInput = new BufferedInputStream(svc
					.getInputStream()
			init(bufferedInput
			super.doSubscribe(subscriber
		}
	}

