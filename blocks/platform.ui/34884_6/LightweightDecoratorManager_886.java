package org.eclipse.ui.internal.decorators;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.ui.internal.ActionExpression;
import org.eclipse.ui.internal.IObjectContributor;
import org.eclipse.ui.internal.LegacyResourceSupport;
import org.eclipse.ui.internal.WorkbenchPlugin;

class LightweightDecoratorDefinition extends DecoratorDefinition implements
		IObjectContributor {

	private static final String ATT_LOCATION = "location"; //$NON-NLS-1$

	static final String ATT_ICON = "icon"; //$NON-NLS-1$

	public static final int TOP_LEFT = 0;

	public static final int TOP_RIGHT = 1;

	public static final int BOTTOM_LEFT = 2;

	public static final int BOTTOM_RIGHT = 3;

	public static final int UNDERLAY = 4;

	private static final String UNDERLAY_STRING = "UNDERLAY"; //$NON-NLS-1$

	public static final int REPLACE = 5;
	
	private static final String REPLACE_STRING = "REPLACE"; //$NON-NLS-1$
	
	private static final String ATT_QUADRANT = "quadrant"; //$NON-NLS-1$

	private static final String TOP_LEFT_STRING = "TOP_LEFT"; //$NON-NLS-1$

	private static final String TOP_RIGHT_STRING = "TOP_RIGHT"; //$NON-NLS-1$

	private static final String BOTTOM_LEFT_STRING = "BOTTOM_LEFT"; //$NON-NLS-1$

	private ILightweightLabelDecorator decorator;

	private int quadrant;

	private boolean hasReadQuadrant;

	private String[] objectClasses;

	LightweightDecoratorDefinition(String identifier,
			IConfigurationElement element) {
		super(identifier, element);
	}

	protected ILightweightLabelDecorator internalGetDecorator()
			throws CoreException {
		if (labelProviderCreationFailed) {
			return null;
		}

		final CoreException[] exceptions = new CoreException[1];

		if (decorator == null) {

			if (isDeclarative()) {
				decorator = new DeclarativeDecorator(definingElement,
						getIconLocation());
			} else {

				SafeRunner.run(new ISafeRunnable() {
					@Override
					public void run() {
						try {
							decorator = (ILightweightLabelDecorator) WorkbenchPlugin
									.createExtension(definingElement,
											DecoratorDefinition.ATT_CLASS);
							decorator.addListener(WorkbenchPlugin.getDefault()
									.getDecoratorManager());
						} catch (CoreException exception) {
							exceptions[0] = exception;
						}
					}

					@Override
					public void handleException(Throwable e) {
					}
				});
			}
		} else {
			return decorator;
		}

		if (decorator == null) {
			this.labelProviderCreationFailed = true;
			setEnabled(false);
		}

		if (exceptions[0] != null) {
			throw exceptions[0];
		}

		return decorator;
	}

	private boolean isDeclarative() {
		return definingElement.getAttribute(DecoratorDefinition.ATT_CLASS) == null;
	}

	private String getIconLocation() {
		return definingElement.getAttribute(ATT_ICON);
	}

	@Override
	protected IBaseLabelProvider internalGetLabelProvider()
			throws CoreException {
		return internalGetDecorator();
	}

	@Override
	public boolean isFull() {
		return false;
	}

	public int getQuadrant() {
		if (!hasReadQuadrant) {
			hasReadQuadrant = true;
			quadrant = getLocationConstant(definingElement
					.getAttribute(ATT_LOCATION), definingElement);
		}
		return quadrant;
	}

	private int getLocationConstant(String locationDefinition,
			IConfigurationElement element) {

		if (locationDefinition == null) {
			locationDefinition = element.getAttribute(ATT_QUADRANT);
		}

		if (TOP_RIGHT_STRING.equals(locationDefinition)) {
			return TOP_RIGHT;
		}
		if (TOP_LEFT_STRING.equals(locationDefinition)) {
			return TOP_LEFT;
		}
		if (BOTTOM_LEFT_STRING.equals(locationDefinition)) {
			return BOTTOM_LEFT;
		}
		if (UNDERLAY_STRING.equals(locationDefinition)) {
			return UNDERLAY;
		}
		if (REPLACE_STRING.equals(locationDefinition)) {
			return REPLACE;
		}
		return BOTTOM_RIGHT;

	}

	public void decorate(Object element, IDecoration decoration) {
		try {
			ILightweightLabelDecorator currentDecorator = internalGetDecorator();
			if(currentDecorator == null) {
				return;
			}
			
			if (isAdaptable()) {
				String[] classes = getObjectClasses();
				for (int i = 0; i < classes.length; i++) {
					String className = classes[i];
					Object adapted = LegacyResourceSupport.getAdapter(element,
							className);
					if (adapted != null) {
						currentDecorator.decorate(adapted, decoration);
					}					
				}				
			}
			else{
				if (currentDecorator != null && element != null) {
					currentDecorator.decorate(element, decoration);
				}
			}
		} catch (CoreException exception) {
			handleCoreException(exception);
		}

	}

	public ILightweightLabelDecorator getDecorator() {
		return decorator;
	}

	@Override
	protected void refreshDecorator() {
		if (!this.enabled && decorator != null) {
			IBaseLabelProvider cached = decorator;
			decorator = null;
			disposeCachedDecorator(cached);
		}
	}

	@Override
	public boolean isApplicableTo(Object object) {
		return isEnabledFor(object);
	}

	@Override
	public boolean canAdapt() {
		return isAdaptable();
	}

	public String[] getObjectClasses() {
		if (objectClasses == null) {
			getEnablement();
		}
		return objectClasses;
	}

	@Override
	protected void initializeEnablement() {
		super.initializeEnablement();
		ActionExpression expression = getEnablement();
		if (expression != null) {
			objectClasses = expression.extractObjectClasses();
		}

		if (objectClasses == null) {
			objectClasses = new String[] {Object.class.getName()};
		}
	}

}
