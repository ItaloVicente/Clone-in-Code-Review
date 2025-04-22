			pageContainerSite = slc.createServiceLocator(getSite(), null, new IDisposable(){
				@Override
				public void dispose() {
					close();
				}
			});
