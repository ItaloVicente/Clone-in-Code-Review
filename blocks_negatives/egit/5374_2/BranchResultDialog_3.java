public class BranchResultDialog extends MessageDialog {
	private static final Image INFO = PlatformUI.getWorkbench()
			.getSharedImages().getImage(ISharedImages.IMG_OBJS_INFO_TSK);

	private final CheckoutResult result;

	private final Repository repository;
