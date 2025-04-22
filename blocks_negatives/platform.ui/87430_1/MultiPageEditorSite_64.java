				multiPageEditor.getSite(), null, new IDisposable(){
					@Override
					public void dispose() {
						getMultiPageEditor().close();
					}
				}, context);
