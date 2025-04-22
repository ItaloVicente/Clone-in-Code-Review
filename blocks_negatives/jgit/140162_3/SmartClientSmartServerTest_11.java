		gs.setUploadPackFactory(new UploadPackFactory<HttpServletRequest>() {
			@Override
			public UploadPack create(HttpServletRequest req, Repository db)
					throws ServiceNotEnabledException,
					ServiceNotAuthorizedException {
				DefaultUploadPackFactory f = new DefaultUploadPackFactory();
				UploadPack up = f.create(req, db);
				if (advertiseRefsHook != null) {
					up.setAdvertiseRefsHook(advertiseRefsHook);
				}
				return up;
			}
		});
