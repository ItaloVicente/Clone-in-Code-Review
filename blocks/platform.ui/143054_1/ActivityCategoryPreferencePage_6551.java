public final class ActivityCategoryPreferencePage extends PreferencePage
		implements IWorkbenchPreferencePage, IExecutableExtension {

	public static final String ACTIVITY_NAME = "activityName"; //$NON-NLS-1$

	public static final String ALLOW_ADVANCED = "allowAdvanced"; //$NON-NLS-1$

	public static final String CAPTION_MESSAGE = "captionMessage"; //$NON-NLS-1$

	public static final String CATEGORY_NAME = "categoryName"; //$NON-NLS-1$

	public static final String ACTIVITY_PROMPT_BUTTON = "activityPromptButton"; //$NON-NLS-1$

	public static final String ACTIVITY_PROMPT_BUTTON_TOOLTIP = "activityPromptButtonTooltip"; //$NON-NLS-1$

	private class CategoryLabelProvider extends LabelProvider implements ITableLabelProvider, IActivityManagerListener {

		private LocalResourceManager manager = new LocalResourceManager(JFaceResources.getResources());

		private ImageDescriptor lockDescriptor;

		private boolean decorate;

		public CategoryLabelProvider(boolean decorate) {
			this.decorate = decorate;
			lockDescriptor = AbstractUIPlugin.imageDescriptorFromPlugin(PlatformUI.PLUGIN_ID,
					"icons/full/ovr16/lock_ovr.png"); //$NON-NLS-1$
		}

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			ICategory category = (ICategory) element;
			ImageDescriptor descriptor = PlatformUI.getWorkbench().getActivitySupport().getImageDescriptor(category);
			if (descriptor != null) {
				try {
					if (decorate) {
						if (isLocked(category)) {
							ImageData originalImageData = descriptor.getImageData();
							OverlayIcon overlay = new OverlayIcon(descriptor, lockDescriptor,
									new Point(originalImageData.width, originalImageData.height));
							return manager.createImage(overlay);
						}
					}

					return manager.createImage(descriptor);
				} catch (DeviceResourceException e) {
					WorkbenchPlugin.log(e);
				}
			}
			return null;
		}
