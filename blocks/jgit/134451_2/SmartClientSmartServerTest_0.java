		gs.setUploadPackFactory(new UploadPackFactory<HttpServletRequest>() {
			@Override
			public UploadPack create(HttpServletRequest req
					throws ServiceNotEnabledException
					ServiceNotAuthorizedException {
				DefaultUploadPackFactory f = new DefaultUploadPackFactory();
				UploadPack up = f.create(req
				if (advertiseRefsHook != null) {
					up.setAdvertiseRefsHook(advertiseRefsHook);
				}
				return up;
			}
		});
