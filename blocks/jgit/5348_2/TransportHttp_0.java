			try {
				svc = new Service(SVC_UPLOAD_PACK);
				init(svc.in
				super.doFetch(monitor
			} finally {
				svc = null;
			}
		}

		@Override
		protected void onReceivePack() {
			svc.finalRequest = true;
