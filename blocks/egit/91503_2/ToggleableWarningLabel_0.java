	public static enum LabelImage {

		INFO(ISharedImages.IMG_OBJS_INFO_TSK),


		WARNING(ISharedImages.IMG_OBJS_WARN_TSK),

		ERROR(ISharedImages.IMG_OBJS_ERROR_TSK);

		private String name;

		private LabelImage(String name) {
			this.name = name;
		}

		Image getImage() {
			return PlatformUI.getWorkbench().getSharedImages().getImage(name);
		}
	}

