				((EclipseContext) context).notifyOnDisposal(new IContextDisposalListener() {
					@Override
					public void disposed(IEclipseContext context) {
						tracked.remove(context);
					}
				});
