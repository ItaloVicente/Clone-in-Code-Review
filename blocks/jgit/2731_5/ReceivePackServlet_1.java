		protected void begin(HttpServletRequest req
				throws IOException
				ServiceNotAuthorizedException {
			ReceivePack rp = receivePackFactory.create(req
			req.setAttribute(ATTRIBUTE_HANDLER
		}

		@Override
		protected void advertise(HttpServletRequest req
