package org.eclipse.e4.ui.model.application.ui.basic.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.e4.core.contexts.IEclipseContext;

import org.eclipse.e4.ui.model.application.commands.MBindingContext;
import org.eclipse.e4.ui.model.application.commands.MBindings;
import org.eclipse.e4.ui.model.application.commands.MHandler;
import org.eclipse.e4.ui.model.application.commands.MHandlerContainer;

import org.eclipse.e4.ui.model.application.commands.impl.CommandsPackageImpl;

import org.eclipse.e4.ui.model.application.impl.ApplicationPackageImpl;
import org.eclipse.e4.ui.model.application.impl.StringToStringMapImpl;

import org.eclipse.e4.ui.model.application.ui.MContext;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.MUILabel;

import org.eclipse.e4.ui.model.application.ui.basic.MFrame;
import org.eclipse.e4.ui.model.application.ui.basic.MFrameElement;

import org.eclipse.e4.ui.model.application.ui.impl.ElementContainerImpl;
import org.eclipse.e4.ui.model.application.ui.impl.UiPackageImpl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;

public class FrameImpl extends ElementContainerImpl<MFrameElement> implements MFrame {
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

	protected FrameImpl() {
		super();
	}

	@Override
	protected EClass eStaticClass() {
		return BasicPackageImpl.Literals.FRAME;
	}

	@Override
	public List<MFrameElement> getChildren() {
		if (children == null) {
			children = new EObjectContainmentWithInverseEList<MFrameElement>(MFrameElement.class, this, BasicPackageImpl.FRAME__CHILDREN, UiPackageImpl.UI_ELEMENT__PARENT) { private static final long serialVersionUID = 1L; @Override public Class<?> getInverseFeatureClass() { return MUIElement.class; } };
		}
		return children;
	}

	@Override
	public void setSelectedElement(MFrameElement newSelectedElement) {
		super.setSelectedElement(newSelectedElement);
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String newLabel) {
		String oldLabel = label;
		label = newLabel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasicPackageImpl.FRAME__LABEL, oldLabel, label));
	}

	public String getIconURI() {
		return iconURI;
	}

	public void setIconURI(String newIconURI) {
		String oldIconURI = iconURI;
		iconURI = newIconURI;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasicPackageImpl.FRAME__ICON_URI, oldIconURI, iconURI));
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String newTooltip) {
		String oldTooltip = tooltip;
		tooltip = newTooltip;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasicPackageImpl.FRAME__TOOLTIP, oldTooltip, tooltip));
	}

	public String getLocalizedLabel() {
		throw new UnsupportedOperationException();
	}

	public String getLocalizedTooltip() {
		throw new UnsupportedOperationException();
	}

	public IEclipseContext getContext() {
		return context;
	}

	public void setContext(IEclipseContext newContext) {
		IEclipseContext oldContext = context;
		context = newContext;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasicPackageImpl.FRAME__CONTEXT, oldContext, context));
	}

	public List<String> getVariables() {
		if (variables == null) {
			variables = new EDataTypeUniqueEList<String>(String.class, this, BasicPackageImpl.FRAME__VARIABLES);
		}
		return variables;
	}

	public Map<String, String> getProperties() {
		if (properties == null) {
			properties = new EcoreEMap<String,String>(ApplicationPackageImpl.Literals.STRING_TO_STRING_MAP, StringToStringMapImpl.class, this, BasicPackageImpl.FRAME__PROPERTIES);
		}
		return properties.map();
	}

	public List<MHandler> getHandlers() {
		if (handlers == null) {
			handlers = new EObjectContainmentEList<MHandler>(MHandler.class, this, BasicPackageImpl.FRAME__HANDLERS);
		}
		return handlers;
	}

	public List<MBindingContext> getBindingContexts() {
		if (bindingContexts == null) {
			bindingContexts = new EObjectResolvingEList<MBindingContext>(MBindingContext.class, this, BasicPackageImpl.FRAME__BINDING_CONTEXTS);
		}
		return bindingContexts;
	}

	public int getX() {
		return x;
	}

	public void setX(int newX) {
		int oldX = x;
		x = newX;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasicPackageImpl.FRAME__X, oldX, x));
	}

	public int getY() {
		return y;
	}

	public void setY(int newY) {
		int oldY = y;
		y = newY;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasicPackageImpl.FRAME__Y, oldY, y));
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int newWidth) {
		int oldWidth = width;
		width = newWidth;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasicPackageImpl.FRAME__WIDTH, oldWidth, width));
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int newHeight) {
		int oldHeight = height;
		height = newHeight;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasicPackageImpl.FRAME__HEIGHT, oldHeight, height));
	}

	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BasicPackageImpl.FRAME__PROPERTIES:
				return ((InternalEList<?>)((EMap.InternalMapView<String, String>)getProperties()).eMap()).basicRemove(otherEnd, msgs);
			case BasicPackageImpl.FRAME__HANDLERS:
				return ((InternalEList<?>)getHandlers()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BasicPackageImpl.FRAME__LABEL:
				return getLabel();
			case BasicPackageImpl.FRAME__ICON_URI:
				return getIconURI();
			case BasicPackageImpl.FRAME__TOOLTIP:
				return getTooltip();
			case BasicPackageImpl.FRAME__LOCALIZED_LABEL:
				return getLocalizedLabel();
			case BasicPackageImpl.FRAME__LOCALIZED_TOOLTIP:
				return getLocalizedTooltip();
			case BasicPackageImpl.FRAME__CONTEXT:
				return getContext();
			case BasicPackageImpl.FRAME__VARIABLES:
				return getVariables();
			case BasicPackageImpl.FRAME__PROPERTIES:
				if (coreType) return ((EMap.InternalMapView<String, String>)getProperties()).eMap();
				else return getProperties();
			case BasicPackageImpl.FRAME__HANDLERS:
				return getHandlers();
			case BasicPackageImpl.FRAME__BINDING_CONTEXTS:
				return getBindingContexts();
			case BasicPackageImpl.FRAME__X:
				return getX();
			case BasicPackageImpl.FRAME__Y:
				return getY();
			case BasicPackageImpl.FRAME__WIDTH:
				return getWidth();
			case BasicPackageImpl.FRAME__HEIGHT:
				return getHeight();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case BasicPackageImpl.FRAME__LABEL:
				setLabel((String)newValue);
				return;
			case BasicPackageImpl.FRAME__ICON_URI:
				setIconURI((String)newValue);
				return;
			case BasicPackageImpl.FRAME__TOOLTIP:
				setTooltip((String)newValue);
				return;
			case BasicPackageImpl.FRAME__CONTEXT:
				setContext((IEclipseContext)newValue);
				return;
			case BasicPackageImpl.FRAME__VARIABLES:
				getVariables().clear();
				getVariables().addAll((Collection<? extends String>)newValue);
				return;
			case BasicPackageImpl.FRAME__PROPERTIES:
				((EStructuralFeature.Setting)((EMap.InternalMapView<String, String>)getProperties()).eMap()).set(newValue);
				return;
			case BasicPackageImpl.FRAME__HANDLERS:
				getHandlers().clear();
				getHandlers().addAll((Collection<? extends MHandler>)newValue);
				return;
			case BasicPackageImpl.FRAME__BINDING_CONTEXTS:
				getBindingContexts().clear();
				getBindingContexts().addAll((Collection<? extends MBindingContext>)newValue);
				return;
			case BasicPackageImpl.FRAME__X:
				setX((Integer)newValue);
				return;
			case BasicPackageImpl.FRAME__Y:
				setY((Integer)newValue);
				return;
			case BasicPackageImpl.FRAME__WIDTH:
				setWidth((Integer)newValue);
				return;
			case BasicPackageImpl.FRAME__HEIGHT:
				setHeight((Integer)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case BasicPackageImpl.FRAME__LABEL:
				setLabel(LABEL_EDEFAULT);
				return;
			case BasicPackageImpl.FRAME__ICON_URI:
				setIconURI(ICON_URI_EDEFAULT);
				return;
			case BasicPackageImpl.FRAME__TOOLTIP:
				setTooltip(TOOLTIP_EDEFAULT);
				return;
			case BasicPackageImpl.FRAME__CONTEXT:
				setContext(CONTEXT_EDEFAULT);
				return;
			case BasicPackageImpl.FRAME__VARIABLES:
				getVariables().clear();
				return;
			case BasicPackageImpl.FRAME__PROPERTIES:
				getProperties().clear();
				return;
			case BasicPackageImpl.FRAME__HANDLERS:
				getHandlers().clear();
				return;
			case BasicPackageImpl.FRAME__BINDING_CONTEXTS:
				getBindingContexts().clear();
				return;
			case BasicPackageImpl.FRAME__X:
				setX(X_EDEFAULT);
				return;
			case BasicPackageImpl.FRAME__Y:
				setY(Y_EDEFAULT);
				return;
			case BasicPackageImpl.FRAME__WIDTH:
				setWidth(WIDTH_EDEFAULT);
				return;
			case BasicPackageImpl.FRAME__HEIGHT:
				setHeight(HEIGHT_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case BasicPackageImpl.FRAME__LABEL:
				return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
			case BasicPackageImpl.FRAME__ICON_URI:
				return ICON_URI_EDEFAULT == null ? iconURI != null : !ICON_URI_EDEFAULT.equals(iconURI);
			case BasicPackageImpl.FRAME__TOOLTIP:
				return TOOLTIP_EDEFAULT == null ? tooltip != null : !TOOLTIP_EDEFAULT.equals(tooltip);
			case BasicPackageImpl.FRAME__LOCALIZED_LABEL:
				return LOCALIZED_LABEL_EDEFAULT == null ? getLocalizedLabel() != null : !LOCALIZED_LABEL_EDEFAULT.equals(getLocalizedLabel());
			case BasicPackageImpl.FRAME__LOCALIZED_TOOLTIP:
				return LOCALIZED_TOOLTIP_EDEFAULT == null ? getLocalizedTooltip() != null : !LOCALIZED_TOOLTIP_EDEFAULT.equals(getLocalizedTooltip());
			case BasicPackageImpl.FRAME__CONTEXT:
				return CONTEXT_EDEFAULT == null ? context != null : !CONTEXT_EDEFAULT.equals(context);
			case BasicPackageImpl.FRAME__VARIABLES:
				return variables != null && !variables.isEmpty();
			case BasicPackageImpl.FRAME__PROPERTIES:
				return properties != null && !properties.isEmpty();
			case BasicPackageImpl.FRAME__HANDLERS:
				return handlers != null && !handlers.isEmpty();
			case BasicPackageImpl.FRAME__BINDING_CONTEXTS:
				return bindingContexts != null && !bindingContexts.isEmpty();
			case BasicPackageImpl.FRAME__X:
				return x != X_EDEFAULT;
			case BasicPackageImpl.FRAME__Y:
				return y != Y_EDEFAULT;
			case BasicPackageImpl.FRAME__WIDTH:
				return width != WIDTH_EDEFAULT;
			case BasicPackageImpl.FRAME__HEIGHT:
				return height != HEIGHT_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == MUILabel.class) {
			switch (derivedFeatureID) {
				case BasicPackageImpl.FRAME__LABEL: return UiPackageImpl.UI_LABEL__LABEL;
				case BasicPackageImpl.FRAME__ICON_URI: return UiPackageImpl.UI_LABEL__ICON_URI;
				case BasicPackageImpl.FRAME__TOOLTIP: return UiPackageImpl.UI_LABEL__TOOLTIP;
				case BasicPackageImpl.FRAME__LOCALIZED_LABEL: return UiPackageImpl.UI_LABEL__LOCALIZED_LABEL;
				case BasicPackageImpl.FRAME__LOCALIZED_TOOLTIP: return UiPackageImpl.UI_LABEL__LOCALIZED_TOOLTIP;
				default: return -1;
			}
		}
		if (baseClass == MContext.class) {
			switch (derivedFeatureID) {
				case BasicPackageImpl.FRAME__CONTEXT: return UiPackageImpl.CONTEXT__CONTEXT;
				case BasicPackageImpl.FRAME__VARIABLES: return UiPackageImpl.CONTEXT__VARIABLES;
				case BasicPackageImpl.FRAME__PROPERTIES: return UiPackageImpl.CONTEXT__PROPERTIES;
				default: return -1;
			}
		}
		if (baseClass == MHandlerContainer.class) {
			switch (derivedFeatureID) {
				case BasicPackageImpl.FRAME__HANDLERS: return CommandsPackageImpl.HANDLER_CONTAINER__HANDLERS;
				default: return -1;
			}
		}
		if (baseClass == MBindings.class) {
			switch (derivedFeatureID) {
				case BasicPackageImpl.FRAME__BINDING_CONTEXTS: return CommandsPackageImpl.BINDINGS__BINDING_CONTEXTS;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == MUILabel.class) {
			switch (baseFeatureID) {
				case UiPackageImpl.UI_LABEL__LABEL: return BasicPackageImpl.FRAME__LABEL;
				case UiPackageImpl.UI_LABEL__ICON_URI: return BasicPackageImpl.FRAME__ICON_URI;
				case UiPackageImpl.UI_LABEL__TOOLTIP: return BasicPackageImpl.FRAME__TOOLTIP;
				case UiPackageImpl.UI_LABEL__LOCALIZED_LABEL: return BasicPackageImpl.FRAME__LOCALIZED_LABEL;
				case UiPackageImpl.UI_LABEL__LOCALIZED_TOOLTIP: return BasicPackageImpl.FRAME__LOCALIZED_TOOLTIP;
				default: return -1;
			}
		}
		if (baseClass == MContext.class) {
			switch (baseFeatureID) {
				case UiPackageImpl.CONTEXT__CONTEXT: return BasicPackageImpl.FRAME__CONTEXT;
				case UiPackageImpl.CONTEXT__VARIABLES: return BasicPackageImpl.FRAME__VARIABLES;
				case UiPackageImpl.CONTEXT__PROPERTIES: return BasicPackageImpl.FRAME__PROPERTIES;
				default: return -1;
			}
		}
		if (baseClass == MHandlerContainer.class) {
			switch (baseFeatureID) {
				case CommandsPackageImpl.HANDLER_CONTAINER__HANDLERS: return BasicPackageImpl.FRAME__HANDLERS;
				default: return -1;
			}
		}
		if (baseClass == MBindings.class) {
			switch (baseFeatureID) {
				case CommandsPackageImpl.BINDINGS__BINDING_CONTEXTS: return BasicPackageImpl.FRAME__BINDING_CONTEXTS;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (label: "); //$NON-NLS-1$
		result.append(label);
		result.append(", iconURI: "); //$NON-NLS-1$
		result.append(iconURI);
		result.append(", tooltip: "); //$NON-NLS-1$
		result.append(tooltip);
		result.append(", context: "); //$NON-NLS-1$
		result.append(context);
		result.append(", variables: "); //$NON-NLS-1$
		result.append(variables);
		result.append(", x: "); //$NON-NLS-1$
		result.append(x);
		result.append(", y: "); //$NON-NLS-1$
		result.append(y);
		result.append(", width: "); //$NON-NLS-1$
		result.append(width);
		result.append(", height: "); //$NON-NLS-1$
		result.append(height);
		result.append(')');
		return result.toString();
	}

} //FrameImpl
