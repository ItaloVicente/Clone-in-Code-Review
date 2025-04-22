				@Override
				public Object compute(IEclipseContext context, String contextKey) {
					if (tracker == null) {
						tracker = new UIExtensionTracker(getWorkbench().getDisplay());
					}
					return tracker;
