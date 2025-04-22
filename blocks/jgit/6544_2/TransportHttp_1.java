	class SmartHttpSubscribeConnection extends BasePackSubscribeConnection {
		SmartHttpSubscribeConnection(InputStream advertisement) {
			super(TransportHttp.this);
			init(advertisement
		}

		public void doSubscribe(ProgressMonitor monitor
				Map<String
				throws InterruptedException

			Service svc = new LongPollService(SVC_PUBLISH_SUBSCRIBE);
			init(svc.getInputStream()
			super.doSubscribe(subscriber
		}
	}

