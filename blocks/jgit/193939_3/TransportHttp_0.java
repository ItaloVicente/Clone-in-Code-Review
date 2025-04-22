		protected void doFetch(ProgressMonitor monitor
				Set<ObjectId> have
				throws TransportException {
			svc = new MultiRequestService(SVC_UPLOAD_PACK);
			try (InputStream svcIn = svc.getInputStream();
					OutputStream svcOut = svc.getOutputStream()) {
				init(svcIn
