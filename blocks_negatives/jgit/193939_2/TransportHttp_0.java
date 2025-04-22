		protected void doFetch(final ProgressMonitor monitor,
				final Collection<Ref> want, final Set<ObjectId> have,
				final OutputStream outputStream) throws TransportException {
			try {
				svc = new MultiRequestService(SVC_UPLOAD_PACK);
				init(svc.getInputStream(), svc.getOutputStream());
