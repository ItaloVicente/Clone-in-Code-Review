				IServiceLocator sl = slc.createServiceLocator(getSite(), null, new IDisposable(){
					@Override
					public void dispose() {
						close();
					}
				});
