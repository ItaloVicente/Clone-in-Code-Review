public class DialogImpl<T extends MUIElement> extends org.eclipse.emf.ecore.impl.MinimalEObjectImpl.Container implements MDialog<T> {
	protected static final String LABEL_EDEFAULT = null;
	protected String label = LABEL_EDEFAULT;
	protected static final String ICON_URI_EDEFAULT = null;
	protected String iconURI = ICON_URI_EDEFAULT;
	protected static final String TOOLTIP_EDEFAULT = null;
	protected String tooltip = TOOLTIP_EDEFAULT;
	protected static final String LOCALIZED_LABEL_EDEFAULT = ""; //$NON-NLS-1$
	protected static final String LOCALIZED_TOOLTIP_EDEFAULT = ""; //$NON-NLS-1$
	protected static final IEclipseContext CONTEXT_EDEFAULT = null;
	protected IEclipseContext context = CONTEXT_EDEFAULT;
	protected EList<String> variables;
	protected EMap<String, String> properties;
	protected EList<MHandler> handlers;
	protected EList<MBindingContext> bindingContexts;
	protected static final int X_EDEFAULT = -2147483648;
	protected int x = X_EDEFAULT;
	protected static final int Y_EDEFAULT = -2147483648;
	protected int y = Y_EDEFAULT;
	protected static final int WIDTH_EDEFAULT = -1;
	protected int width = WIDTH_EDEFAULT;
	protected static final int HEIGHT_EDEFAULT = -1;
	protected int height = HEIGHT_EDEFAULT;

