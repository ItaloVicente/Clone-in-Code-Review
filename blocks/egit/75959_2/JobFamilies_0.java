
	private static final class JobFamily {

		protected JobFamily(final ImageDescriptor imageDescriptor) {
			IProgressService service = PlatformUI.getWorkbench()
					.getProgressService();
			service.registerIconForFamily(imageDescriptor, this);
		}

		protected JobFamily() {
			this(UIIcons.REPOSITORY);
		}
	}

