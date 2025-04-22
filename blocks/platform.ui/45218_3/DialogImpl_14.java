	public String getLabel() {
		return label;
	}

	public void setLabel(String newLabel) {
		String oldLabel = label;
		label = newLabel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasicPackageImpl.DIALOG__LABEL, oldLabel, label));
	}

	public String getIconURI() {
		return iconURI;
	}

	public void setIconURI(String newIconURI) {
		String oldIconURI = iconURI;
		iconURI = newIconURI;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasicPackageImpl.DIALOG__ICON_URI, oldIconURI, iconURI));
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String newTooltip) {
		String oldTooltip = tooltip;
		tooltip = newTooltip;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasicPackageImpl.DIALOG__TOOLTIP, oldTooltip, tooltip));
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
			eNotify(new ENotificationImpl(this, Notification.SET, BasicPackageImpl.DIALOG__CONTEXT, oldContext, context));
	}

	public List<String> getVariables() {
		if (variables == null) {
			variables = new EDataTypeUniqueEList<String>(String.class, this, BasicPackageImpl.DIALOG__VARIABLES);
		}
		return variables;
	}

	public Map<String, String> getProperties() {
		if (properties == null) {
			properties = new EcoreEMap<String,String>(ApplicationPackageImpl.Literals.STRING_TO_STRING_MAP, StringToStringMapImpl.class, this, BasicPackageImpl.DIALOG__PROPERTIES);
		}
		return properties.map();
	}

	public List<MHandler> getHandlers() {
		if (handlers == null) {
			handlers = new EObjectContainmentEList<MHandler>(MHandler.class, this, BasicPackageImpl.DIALOG__HANDLERS);
		}
		return handlers;
	}

	public List<MBindingContext> getBindingContexts() {
		if (bindingContexts == null) {
			bindingContexts = new EObjectResolvingEList<MBindingContext>(MBindingContext.class, this, BasicPackageImpl.DIALOG__BINDING_CONTEXTS);
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
			eNotify(new ENotificationImpl(this, Notification.SET, BasicPackageImpl.DIALOG__X, oldX, x));
	}

	public int getY() {
		return y;
	}

	public void setY(int newY) {
		int oldY = y;
		y = newY;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasicPackageImpl.DIALOG__Y, oldY, y));
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int newWidth) {
		int oldWidth = width;
		width = newWidth;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasicPackageImpl.DIALOG__WIDTH, oldWidth, width));
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int newHeight) {
		int oldHeight = height;
		height = newHeight;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasicPackageImpl.DIALOG__HEIGHT, oldHeight, height));
	}

	public void updateLocalization() {
		throw new UnsupportedOperationException();
	}

	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BasicPackageImpl.DIALOG__PROPERTIES:
				return ((InternalEList<?>)((EMap.InternalMapView<String, String>)getProperties()).eMap()).basicRemove(otherEnd, msgs);
			case BasicPackageImpl.DIALOG__HANDLERS:
				return ((InternalEList<?>)getHandlers()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BasicPackageImpl.DIALOG__LABEL:
				return getLabel();
			case BasicPackageImpl.DIALOG__ICON_URI:
				return getIconURI();
			case BasicPackageImpl.DIALOG__TOOLTIP:
				return getTooltip();
			case BasicPackageImpl.DIALOG__LOCALIZED_LABEL:
				return getLocalizedLabel();
			case BasicPackageImpl.DIALOG__LOCALIZED_TOOLTIP:
				return getLocalizedTooltip();
			case BasicPackageImpl.DIALOG__CONTEXT:
				return getContext();
			case BasicPackageImpl.DIALOG__VARIABLES:
				return getVariables();
			case BasicPackageImpl.DIALOG__PROPERTIES:
				if (coreType) return ((EMap.InternalMapView<String, String>)getProperties()).eMap();
				else return getProperties();
			case BasicPackageImpl.DIALOG__HANDLERS:
				return getHandlers();
			case BasicPackageImpl.DIALOG__BINDING_CONTEXTS:
				return getBindingContexts();
			case BasicPackageImpl.DIALOG__X:
				return getX();
			case BasicPackageImpl.DIALOG__Y:
				return getY();
			case BasicPackageImpl.DIALOG__WIDTH:
				return getWidth();
			case BasicPackageImpl.DIALOG__HEIGHT:
				return getHeight();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case BasicPackageImpl.DIALOG__LABEL:
				setLabel((String)newValue);
				return;
			case BasicPackageImpl.DIALOG__ICON_URI:
				setIconURI((String)newValue);
				return;
			case BasicPackageImpl.DIALOG__TOOLTIP:
				setTooltip((String)newValue);
				return;
			case BasicPackageImpl.DIALOG__CONTEXT:
				setContext((IEclipseContext)newValue);
				return;
			case BasicPackageImpl.DIALOG__VARIABLES:
				getVariables().clear();
				getVariables().addAll((Collection<? extends String>)newValue);
				return;
			case BasicPackageImpl.DIALOG__PROPERTIES:
				((EStructuralFeature.Setting)((EMap.InternalMapView<String, String>)getProperties()).eMap()).set(newValue);
				return;
			case BasicPackageImpl.DIALOG__HANDLERS:
				getHandlers().clear();
				getHandlers().addAll((Collection<? extends MHandler>)newValue);
				return;
			case BasicPackageImpl.DIALOG__BINDING_CONTEXTS:
				getBindingContexts().clear();
				getBindingContexts().addAll((Collection<? extends MBindingContext>)newValue);
				return;
			case BasicPackageImpl.DIALOG__X:
				setX((Integer)newValue);
				return;
			case BasicPackageImpl.DIALOG__Y:
				setY((Integer)newValue);
				return;
			case BasicPackageImpl.DIALOG__WIDTH:
				setWidth((Integer)newValue);
				return;
			case BasicPackageImpl.DIALOG__HEIGHT:
				setHeight((Integer)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case BasicPackageImpl.DIALOG__LABEL:
				setLabel(LABEL_EDEFAULT);
				return;
			case BasicPackageImpl.DIALOG__ICON_URI:
				setIconURI(ICON_URI_EDEFAULT);
				return;
			case BasicPackageImpl.DIALOG__TOOLTIP:
				setTooltip(TOOLTIP_EDEFAULT);
				return;
			case BasicPackageImpl.DIALOG__CONTEXT:
				setContext(CONTEXT_EDEFAULT);
				return;
			case BasicPackageImpl.DIALOG__VARIABLES:
				getVariables().clear();
				return;
			case BasicPackageImpl.DIALOG__PROPERTIES:
				getProperties().clear();
				return;
			case BasicPackageImpl.DIALOG__HANDLERS:
				getHandlers().clear();
				return;
			case BasicPackageImpl.DIALOG__BINDING_CONTEXTS:
				getBindingContexts().clear();
				return;
			case BasicPackageImpl.DIALOG__X:
				setX(X_EDEFAULT);
				return;
			case BasicPackageImpl.DIALOG__Y:
				setY(Y_EDEFAULT);
				return;
			case BasicPackageImpl.DIALOG__WIDTH:
				setWidth(WIDTH_EDEFAULT);
				return;
			case BasicPackageImpl.DIALOG__HEIGHT:
				setHeight(HEIGHT_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case BasicPackageImpl.DIALOG__LABEL:
				return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
			case BasicPackageImpl.DIALOG__ICON_URI:
				return ICON_URI_EDEFAULT == null ? iconURI != null : !ICON_URI_EDEFAULT.equals(iconURI);
			case BasicPackageImpl.DIALOG__TOOLTIP:
				return TOOLTIP_EDEFAULT == null ? tooltip != null : !TOOLTIP_EDEFAULT.equals(tooltip);
			case BasicPackageImpl.DIALOG__LOCALIZED_LABEL:
				return LOCALIZED_LABEL_EDEFAULT == null ? getLocalizedLabel() != null : !LOCALIZED_LABEL_EDEFAULT.equals(getLocalizedLabel());
			case BasicPackageImpl.DIALOG__LOCALIZED_TOOLTIP:
				return LOCALIZED_TOOLTIP_EDEFAULT == null ? getLocalizedTooltip() != null : !LOCALIZED_TOOLTIP_EDEFAULT.equals(getLocalizedTooltip());
			case BasicPackageImpl.DIALOG__CONTEXT:
				return CONTEXT_EDEFAULT == null ? context != null : !CONTEXT_EDEFAULT.equals(context);
			case BasicPackageImpl.DIALOG__VARIABLES:
				return variables != null && !variables.isEmpty();
			case BasicPackageImpl.DIALOG__PROPERTIES:
				return properties != null && !properties.isEmpty();
			case BasicPackageImpl.DIALOG__HANDLERS:
				return handlers != null && !handlers.isEmpty();
			case BasicPackageImpl.DIALOG__BINDING_CONTEXTS:
				return bindingContexts != null && !bindingContexts.isEmpty();
			case BasicPackageImpl.DIALOG__X:
				return x != X_EDEFAULT;
			case BasicPackageImpl.DIALOG__Y:
				return y != Y_EDEFAULT;
			case BasicPackageImpl.DIALOG__WIDTH:
				return width != WIDTH_EDEFAULT;
			case BasicPackageImpl.DIALOG__HEIGHT:
				return height != HEIGHT_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == MContext.class) {
			switch (derivedFeatureID) {
				case BasicPackageImpl.DIALOG__CONTEXT: return UiPackageImpl.CONTEXT__CONTEXT;
				case BasicPackageImpl.DIALOG__VARIABLES: return UiPackageImpl.CONTEXT__VARIABLES;
				case BasicPackageImpl.DIALOG__PROPERTIES: return UiPackageImpl.CONTEXT__PROPERTIES;
				default: return -1;
			}
		}
		if (baseClass == MHandlerContainer.class) {
			switch (derivedFeatureID) {
				case BasicPackageImpl.DIALOG__HANDLERS: return CommandsPackageImpl.HANDLER_CONTAINER__HANDLERS;
				default: return -1;
			}
		}
		if (baseClass == MBindings.class) {
			switch (derivedFeatureID) {
				case BasicPackageImpl.DIALOG__BINDING_CONTEXTS: return CommandsPackageImpl.BINDINGS__BINDING_CONTEXTS;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == MContext.class) {
			switch (baseFeatureID) {
				case UiPackageImpl.CONTEXT__CONTEXT: return BasicPackageImpl.DIALOG__CONTEXT;
				case UiPackageImpl.CONTEXT__VARIABLES: return BasicPackageImpl.DIALOG__VARIABLES;
				case UiPackageImpl.CONTEXT__PROPERTIES: return BasicPackageImpl.DIALOG__PROPERTIES;
				default: return -1;
			}
		}
		if (baseClass == MHandlerContainer.class) {
			switch (baseFeatureID) {
				case CommandsPackageImpl.HANDLER_CONTAINER__HANDLERS: return BasicPackageImpl.DIALOG__HANDLERS;
				default: return -1;
			}
		}
		if (baseClass == MBindings.class) {
			switch (baseFeatureID) {
				case CommandsPackageImpl.BINDINGS__BINDING_CONTEXTS: return BasicPackageImpl.DIALOG__BINDING_CONTEXTS;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case BasicPackageImpl.DIALOG___UPDATE_LOCALIZATION:
				updateLocalization();
				return null;
		}
		return super.eInvoke(operationID, arguments);
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

