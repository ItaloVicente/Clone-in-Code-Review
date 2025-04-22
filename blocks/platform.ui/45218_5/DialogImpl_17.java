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

	public String getElementId() {
		return elementId;
	}

	public void setElementId(String newElementId) {
		String oldElementId = elementId;
		elementId = newElementId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasicPackageImpl.DIALOG__ELEMENT_ID, oldElementId, elementId));
	}

	public Map<String, String> getPersistedState() {
		if (persistedState == null) {
			persistedState = new EcoreEMap<String,String>(ApplicationPackageImpl.Literals.STRING_TO_STRING_MAP, StringToStringMapImpl.class, this, BasicPackageImpl.DIALOG__PERSISTED_STATE);
		}
		return persistedState.map();
	}

	public List<String> getTags() {
		if (tags == null) {
			tags = new EDataTypeUniqueEList<String>(String.class, this, BasicPackageImpl.DIALOG__TAGS);
		}
		return tags;
	}

	public String getContributorURI() {
		return contributorURI;
	}

	public void setContributorURI(String newContributorURI) {
		String oldContributorURI = contributorURI;
		contributorURI = newContributorURI;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasicPackageImpl.DIALOG__CONTRIBUTOR_URI, oldContributorURI, contributorURI));
	}

	public Map<String, Object> getTransientData() {
		if (transientData == null) {
			transientData = new EcoreEMap<String,Object>(ApplicationPackageImpl.Literals.STRING_TO_OBJECT_MAP, StringToObjectMapImpl.class, this, BasicPackageImpl.DIALOG__TRANSIENT_DATA);
		}
		return transientData.map();
	}

	public Object getWidget() {
		return widget;
	}

	public void setWidget(Object newWidget) {
		Object oldWidget = widget;
		widget = newWidget;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasicPackageImpl.DIALOG__WIDGET, oldWidget, widget));
	}

	public Object getRenderer() {
		return renderer;
	}

	public void setRenderer(Object newRenderer) {
		Object oldRenderer = renderer;
		renderer = newRenderer;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasicPackageImpl.DIALOG__RENDERER, oldRenderer, renderer));
	}

	public boolean isToBeRendered() {
		return toBeRendered;
	}

	public void setToBeRendered(boolean newToBeRendered) {
		boolean oldToBeRendered = toBeRendered;
		toBeRendered = newToBeRendered;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasicPackageImpl.DIALOG__TO_BE_RENDERED, oldToBeRendered, toBeRendered));
	}

	public boolean isOnTop() {
		return onTop;
	}

	public void setOnTop(boolean newOnTop) {
		boolean oldOnTop = onTop;
		onTop = newOnTop;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasicPackageImpl.DIALOG__ON_TOP, oldOnTop, onTop));
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean newVisible) {
		boolean oldVisible = visible;
		visible = newVisible;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasicPackageImpl.DIALOG__VISIBLE, oldVisible, visible));
	}

	@SuppressWarnings("unchecked")
	public MElementContainer<MUIElement> getParent() {
		if (eContainerFeatureID() != BasicPackageImpl.DIALOG__PARENT) return null;
		return (MElementContainer<MUIElement>)eInternalContainer();
	}

	public NotificationChain basicSetParent(MElementContainer<MUIElement> newParent, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newParent, BasicPackageImpl.DIALOG__PARENT, msgs);
		return msgs;
	}

	public void setParent(MElementContainer<MUIElement> newParent) {
		if (newParent != eInternalContainer() || (eContainerFeatureID() != BasicPackageImpl.DIALOG__PARENT && newParent != null)) {
			if (EcoreUtil.isAncestor(this, (EObject)newParent))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newParent != null)
				msgs = ((InternalEObject)newParent).eInverseAdd(this, UiPackageImpl.ELEMENT_CONTAINER__CHILDREN, MElementContainer.class, msgs);
			msgs = basicSetParent(newParent, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasicPackageImpl.DIALOG__PARENT, newParent, newParent));
	}

	public String getContainerData() {
		return containerData;
	}

	public void setContainerData(String newContainerData) {
		String oldContainerData = containerData;
		containerData = newContainerData;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasicPackageImpl.DIALOG__CONTAINER_DATA, oldContainerData, containerData));
	}

	public MPlaceholder getCurSharedRef() {
		if (curSharedRef != null && ((EObject)curSharedRef).eIsProxy()) {
			InternalEObject oldCurSharedRef = (InternalEObject)curSharedRef;
			curSharedRef = (MPlaceholder)eResolveProxy(oldCurSharedRef);
			if (curSharedRef != oldCurSharedRef) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BasicPackageImpl.DIALOG__CUR_SHARED_REF, oldCurSharedRef, curSharedRef));
			}
		}
		return curSharedRef;
	}

	public MPlaceholder basicGetCurSharedRef() {
		return curSharedRef;
	}

	public void setCurSharedRef(MPlaceholder newCurSharedRef) {
		MPlaceholder oldCurSharedRef = curSharedRef;
		curSharedRef = newCurSharedRef;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasicPackageImpl.DIALOG__CUR_SHARED_REF, oldCurSharedRef, curSharedRef));
	}

	public MExpression getVisibleWhen() {
		return visibleWhen;
	}

	public NotificationChain basicSetVisibleWhen(MExpression newVisibleWhen, NotificationChain msgs) {
		MExpression oldVisibleWhen = visibleWhen;
		visibleWhen = newVisibleWhen;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BasicPackageImpl.DIALOG__VISIBLE_WHEN, oldVisibleWhen, newVisibleWhen);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	public void setVisibleWhen(MExpression newVisibleWhen) {
		if (newVisibleWhen != visibleWhen) {
			NotificationChain msgs = null;
			if (visibleWhen != null)
				msgs = ((InternalEObject)visibleWhen).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BasicPackageImpl.DIALOG__VISIBLE_WHEN, null, msgs);
			if (newVisibleWhen != null)
				msgs = ((InternalEObject)newVisibleWhen).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BasicPackageImpl.DIALOG__VISIBLE_WHEN, null, msgs);
			msgs = basicSetVisibleWhen(newVisibleWhen, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasicPackageImpl.DIALOG__VISIBLE_WHEN, newVisibleWhen, newVisibleWhen));
	}

	public String getAccessibilityPhrase() {
		return accessibilityPhrase;
	}

	public void setAccessibilityPhrase(String newAccessibilityPhrase) {
		String oldAccessibilityPhrase = accessibilityPhrase;
		accessibilityPhrase = newAccessibilityPhrase;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasicPackageImpl.DIALOG__ACCESSIBILITY_PHRASE, oldAccessibilityPhrase, accessibilityPhrase));
	}

	public String getLocalizedAccessibilityPhrase() {
		throw new UnsupportedOperationException();
	}

	public List<MPartSashContainerElement> getChildren() {
		if (children == null) {
			children = new EObjectContainmentWithInverseEList<MPartSashContainerElement>(MPartSashContainerElement.class, this, BasicPackageImpl.DIALOG__CHILDREN, UiPackageImpl.UI_ELEMENT__PARENT) { private static final long serialVersionUID = 1L; @Override public Class<?> getInverseFeatureClass() { return MUIElement.class; } };
		}
		return children;
	}

	public MPartSashContainerElement getSelectedElement() {
		if (selectedElement != null && ((EObject)selectedElement).eIsProxy()) {
			InternalEObject oldSelectedElement = (InternalEObject)selectedElement;
			selectedElement = (MPartSashContainerElement)eResolveProxy(oldSelectedElement);
			if (selectedElement != oldSelectedElement) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BasicPackageImpl.DIALOG__SELECTED_ELEMENT, oldSelectedElement, selectedElement));
			}
		}
		return selectedElement;
	}

	public MPartSashContainerElement basicGetSelectedElement() {
		return selectedElement;
	}

	public void setSelectedElement(MPartSashContainerElement newSelectedElement) {
		MPartSashContainerElement oldSelectedElement = selectedElement;
		selectedElement = newSelectedElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasicPackageImpl.DIALOG__SELECTED_ELEMENT, oldSelectedElement, selectedElement));
	}

	public boolean isHorizontal() {
		return horizontal;
	}

	public void setHorizontal(boolean newHorizontal) {
		boolean oldHorizontal = horizontal;
		horizontal = newHorizontal;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasicPackageImpl.DIALOG__HORIZONTAL, oldHorizontal, horizontal));
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

	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BasicPackageImpl.DIALOG__PARENT:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetParent((MElementContainer<MUIElement>)otherEnd, msgs);
			case BasicPackageImpl.DIALOG__CHILDREN:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getChildren()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BasicPackageImpl.DIALOG__PERSISTED_STATE:
				return ((InternalEList<?>)((EMap.InternalMapView<String, String>)getPersistedState()).eMap()).basicRemove(otherEnd, msgs);
			case BasicPackageImpl.DIALOG__TRANSIENT_DATA:
				return ((InternalEList<?>)((EMap.InternalMapView<String, Object>)getTransientData()).eMap()).basicRemove(otherEnd, msgs);
			case BasicPackageImpl.DIALOG__PARENT:
				return basicSetParent(null, msgs);
			case BasicPackageImpl.DIALOG__VISIBLE_WHEN:
				return basicSetVisibleWhen(null, msgs);
			case BasicPackageImpl.DIALOG__CHILDREN:
				return ((InternalEList<?>)getChildren()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case BasicPackageImpl.DIALOG__PARENT:
				return eInternalContainer().eInverseRemove(this, UiPackageImpl.ELEMENT_CONTAINER__CHILDREN, MElementContainer.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
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
			case BasicPackageImpl.DIALOG__ELEMENT_ID:
				return getElementId();
			case BasicPackageImpl.DIALOG__PERSISTED_STATE:
				if (coreType) return ((EMap.InternalMapView<String, String>)getPersistedState()).eMap();
				else return getPersistedState();
			case BasicPackageImpl.DIALOG__TAGS:
				return getTags();
			case BasicPackageImpl.DIALOG__CONTRIBUTOR_URI:
				return getContributorURI();
			case BasicPackageImpl.DIALOG__TRANSIENT_DATA:
				if (coreType) return ((EMap.InternalMapView<String, Object>)getTransientData()).eMap();
				else return getTransientData();
			case BasicPackageImpl.DIALOG__WIDGET:
				return getWidget();
			case BasicPackageImpl.DIALOG__RENDERER:
				return getRenderer();
			case BasicPackageImpl.DIALOG__TO_BE_RENDERED:
				return isToBeRendered();
			case BasicPackageImpl.DIALOG__ON_TOP:
				return isOnTop();
			case BasicPackageImpl.DIALOG__VISIBLE:
				return isVisible();
			case BasicPackageImpl.DIALOG__PARENT:
				return getParent();
			case BasicPackageImpl.DIALOG__CONTAINER_DATA:
				return getContainerData();
			case BasicPackageImpl.DIALOG__CUR_SHARED_REF:
				if (resolve) return getCurSharedRef();
				return basicGetCurSharedRef();
			case BasicPackageImpl.DIALOG__VISIBLE_WHEN:
				return getVisibleWhen();
			case BasicPackageImpl.DIALOG__ACCESSIBILITY_PHRASE:
				return getAccessibilityPhrase();
			case BasicPackageImpl.DIALOG__LOCALIZED_ACCESSIBILITY_PHRASE:
				return getLocalizedAccessibilityPhrase();
			case BasicPackageImpl.DIALOG__CHILDREN:
				return getChildren();
			case BasicPackageImpl.DIALOG__SELECTED_ELEMENT:
				if (resolve) return getSelectedElement();
				return basicGetSelectedElement();
			case BasicPackageImpl.DIALOG__HORIZONTAL:
				return isHorizontal();
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
			case BasicPackageImpl.DIALOG__ELEMENT_ID:
				setElementId((String)newValue);
				return;
			case BasicPackageImpl.DIALOG__PERSISTED_STATE:
				((EStructuralFeature.Setting)((EMap.InternalMapView<String, String>)getPersistedState()).eMap()).set(newValue);
				return;
			case BasicPackageImpl.DIALOG__TAGS:
				getTags().clear();
				getTags().addAll((Collection<? extends String>)newValue);
				return;
			case BasicPackageImpl.DIALOG__CONTRIBUTOR_URI:
				setContributorURI((String)newValue);
				return;
			case BasicPackageImpl.DIALOG__TRANSIENT_DATA:
				((EStructuralFeature.Setting)((EMap.InternalMapView<String, Object>)getTransientData()).eMap()).set(newValue);
				return;
			case BasicPackageImpl.DIALOG__WIDGET:
				setWidget(newValue);
				return;
			case BasicPackageImpl.DIALOG__RENDERER:
				setRenderer(newValue);
				return;
			case BasicPackageImpl.DIALOG__TO_BE_RENDERED:
				setToBeRendered((Boolean)newValue);
				return;
			case BasicPackageImpl.DIALOG__ON_TOP:
				setOnTop((Boolean)newValue);
				return;
			case BasicPackageImpl.DIALOG__VISIBLE:
				setVisible((Boolean)newValue);
				return;
			case BasicPackageImpl.DIALOG__PARENT:
				setParent((MElementContainer<MUIElement>)newValue);
				return;
			case BasicPackageImpl.DIALOG__CONTAINER_DATA:
				setContainerData((String)newValue);
				return;
			case BasicPackageImpl.DIALOG__CUR_SHARED_REF:
				setCurSharedRef((MPlaceholder)newValue);
				return;
			case BasicPackageImpl.DIALOG__VISIBLE_WHEN:
				setVisibleWhen((MExpression)newValue);
				return;
			case BasicPackageImpl.DIALOG__ACCESSIBILITY_PHRASE:
				setAccessibilityPhrase((String)newValue);
				return;
			case BasicPackageImpl.DIALOG__CHILDREN:
				getChildren().clear();
				getChildren().addAll((Collection<? extends MPartSashContainerElement>)newValue);
				return;
			case BasicPackageImpl.DIALOG__SELECTED_ELEMENT:
				setSelectedElement((MPartSashContainerElement)newValue);
				return;
			case BasicPackageImpl.DIALOG__HORIZONTAL:
				setHorizontal((Boolean)newValue);
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
			case BasicPackageImpl.DIALOG__ELEMENT_ID:
				setElementId(ELEMENT_ID_EDEFAULT);
				return;
			case BasicPackageImpl.DIALOG__PERSISTED_STATE:
				getPersistedState().clear();
				return;
			case BasicPackageImpl.DIALOG__TAGS:
				getTags().clear();
				return;
			case BasicPackageImpl.DIALOG__CONTRIBUTOR_URI:
				setContributorURI(CONTRIBUTOR_URI_EDEFAULT);
				return;
			case BasicPackageImpl.DIALOG__TRANSIENT_DATA:
				getTransientData().clear();
				return;
			case BasicPackageImpl.DIALOG__WIDGET:
				setWidget(WIDGET_EDEFAULT);
				return;
			case BasicPackageImpl.DIALOG__RENDERER:
				setRenderer(RENDERER_EDEFAULT);
				return;
			case BasicPackageImpl.DIALOG__TO_BE_RENDERED:
				setToBeRendered(TO_BE_RENDERED_EDEFAULT);
				return;
			case BasicPackageImpl.DIALOG__ON_TOP:
				setOnTop(ON_TOP_EDEFAULT);
				return;
			case BasicPackageImpl.DIALOG__VISIBLE:
				setVisible(VISIBLE_EDEFAULT);
				return;
			case BasicPackageImpl.DIALOG__PARENT:
				setParent((MElementContainer<MUIElement>)null);
				return;
			case BasicPackageImpl.DIALOG__CONTAINER_DATA:
				setContainerData(CONTAINER_DATA_EDEFAULT);
				return;
			case BasicPackageImpl.DIALOG__CUR_SHARED_REF:
				setCurSharedRef((MPlaceholder)null);
				return;
			case BasicPackageImpl.DIALOG__VISIBLE_WHEN:
				setVisibleWhen((MExpression)null);
				return;
			case BasicPackageImpl.DIALOG__ACCESSIBILITY_PHRASE:
				setAccessibilityPhrase(ACCESSIBILITY_PHRASE_EDEFAULT);
				return;
			case BasicPackageImpl.DIALOG__CHILDREN:
				getChildren().clear();
				return;
			case BasicPackageImpl.DIALOG__SELECTED_ELEMENT:
				setSelectedElement((MPartSashContainerElement)null);
				return;
			case BasicPackageImpl.DIALOG__HORIZONTAL:
				setHorizontal(HORIZONTAL_EDEFAULT);
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
			case BasicPackageImpl.DIALOG__ELEMENT_ID:
				return ELEMENT_ID_EDEFAULT == null ? elementId != null : !ELEMENT_ID_EDEFAULT.equals(elementId);
			case BasicPackageImpl.DIALOG__PERSISTED_STATE:
				return persistedState != null && !persistedState.isEmpty();
			case BasicPackageImpl.DIALOG__TAGS:
				return tags != null && !tags.isEmpty();
			case BasicPackageImpl.DIALOG__CONTRIBUTOR_URI:
				return CONTRIBUTOR_URI_EDEFAULT == null ? contributorURI != null : !CONTRIBUTOR_URI_EDEFAULT.equals(contributorURI);
			case BasicPackageImpl.DIALOG__TRANSIENT_DATA:
				return transientData != null && !transientData.isEmpty();
			case BasicPackageImpl.DIALOG__WIDGET:
				return WIDGET_EDEFAULT == null ? widget != null : !WIDGET_EDEFAULT.equals(widget);
			case BasicPackageImpl.DIALOG__RENDERER:
				return RENDERER_EDEFAULT == null ? renderer != null : !RENDERER_EDEFAULT.equals(renderer);
			case BasicPackageImpl.DIALOG__TO_BE_RENDERED:
				return toBeRendered != TO_BE_RENDERED_EDEFAULT;
			case BasicPackageImpl.DIALOG__ON_TOP:
				return onTop != ON_TOP_EDEFAULT;
			case BasicPackageImpl.DIALOG__VISIBLE:
				return visible != VISIBLE_EDEFAULT;
			case BasicPackageImpl.DIALOG__PARENT:
				return getParent() != null;
			case BasicPackageImpl.DIALOG__CONTAINER_DATA:
				return CONTAINER_DATA_EDEFAULT == null ? containerData != null : !CONTAINER_DATA_EDEFAULT.equals(containerData);
			case BasicPackageImpl.DIALOG__CUR_SHARED_REF:
				return curSharedRef != null;
			case BasicPackageImpl.DIALOG__VISIBLE_WHEN:
				return visibleWhen != null;
			case BasicPackageImpl.DIALOG__ACCESSIBILITY_PHRASE:
				return ACCESSIBILITY_PHRASE_EDEFAULT == null ? accessibilityPhrase != null : !ACCESSIBILITY_PHRASE_EDEFAULT.equals(accessibilityPhrase);
			case BasicPackageImpl.DIALOG__LOCALIZED_ACCESSIBILITY_PHRASE:
				return LOCALIZED_ACCESSIBILITY_PHRASE_EDEFAULT == null ? getLocalizedAccessibilityPhrase() != null : !LOCALIZED_ACCESSIBILITY_PHRASE_EDEFAULT.equals(getLocalizedAccessibilityPhrase());
			case BasicPackageImpl.DIALOG__CHILDREN:
				return children != null && !children.isEmpty();
			case BasicPackageImpl.DIALOG__SELECTED_ELEMENT:
				return selectedElement != null;
			case BasicPackageImpl.DIALOG__HORIZONTAL:
				return horizontal != HORIZONTAL_EDEFAULT;
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
		if (baseClass == MApplicationElement.class) {
			switch (derivedFeatureID) {
				case BasicPackageImpl.DIALOG__ELEMENT_ID: return ApplicationPackageImpl.APPLICATION_ELEMENT__ELEMENT_ID;
				case BasicPackageImpl.DIALOG__PERSISTED_STATE: return ApplicationPackageImpl.APPLICATION_ELEMENT__PERSISTED_STATE;
				case BasicPackageImpl.DIALOG__TAGS: return ApplicationPackageImpl.APPLICATION_ELEMENT__TAGS;
				case BasicPackageImpl.DIALOG__CONTRIBUTOR_URI: return ApplicationPackageImpl.APPLICATION_ELEMENT__CONTRIBUTOR_URI;
				case BasicPackageImpl.DIALOG__TRANSIENT_DATA: return ApplicationPackageImpl.APPLICATION_ELEMENT__TRANSIENT_DATA;
				default: return -1;
			}
		}
		if (baseClass == MUIElement.class) {
			switch (derivedFeatureID) {
				case BasicPackageImpl.DIALOG__WIDGET: return UiPackageImpl.UI_ELEMENT__WIDGET;
				case BasicPackageImpl.DIALOG__RENDERER: return UiPackageImpl.UI_ELEMENT__RENDERER;
				case BasicPackageImpl.DIALOG__TO_BE_RENDERED: return UiPackageImpl.UI_ELEMENT__TO_BE_RENDERED;
				case BasicPackageImpl.DIALOG__ON_TOP: return UiPackageImpl.UI_ELEMENT__ON_TOP;
				case BasicPackageImpl.DIALOG__VISIBLE: return UiPackageImpl.UI_ELEMENT__VISIBLE;
				case BasicPackageImpl.DIALOG__PARENT: return UiPackageImpl.UI_ELEMENT__PARENT;
				case BasicPackageImpl.DIALOG__CONTAINER_DATA: return UiPackageImpl.UI_ELEMENT__CONTAINER_DATA;
				case BasicPackageImpl.DIALOG__CUR_SHARED_REF: return UiPackageImpl.UI_ELEMENT__CUR_SHARED_REF;
				case BasicPackageImpl.DIALOG__VISIBLE_WHEN: return UiPackageImpl.UI_ELEMENT__VISIBLE_WHEN;
				case BasicPackageImpl.DIALOG__ACCESSIBILITY_PHRASE: return UiPackageImpl.UI_ELEMENT__ACCESSIBILITY_PHRASE;
				case BasicPackageImpl.DIALOG__LOCALIZED_ACCESSIBILITY_PHRASE: return UiPackageImpl.UI_ELEMENT__LOCALIZED_ACCESSIBILITY_PHRASE;
				default: return -1;
			}
		}
		if (baseClass == MElementContainer.class) {
			switch (derivedFeatureID) {
				case BasicPackageImpl.DIALOG__CHILDREN: return UiPackageImpl.ELEMENT_CONTAINER__CHILDREN;
				case BasicPackageImpl.DIALOG__SELECTED_ELEMENT: return UiPackageImpl.ELEMENT_CONTAINER__SELECTED_ELEMENT;
				default: return -1;
			}
		}
		if (baseClass == MGenericTile.class) {
			switch (derivedFeatureID) {
				case BasicPackageImpl.DIALOG__HORIZONTAL: return UiPackageImpl.GENERIC_TILE__HORIZONTAL;
				default: return -1;
			}
		}
		if (baseClass == MFrameElement.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == MApplicationElement.class) {
			switch (baseFeatureID) {
				case ApplicationPackageImpl.APPLICATION_ELEMENT__ELEMENT_ID: return BasicPackageImpl.DIALOG__ELEMENT_ID;
				case ApplicationPackageImpl.APPLICATION_ELEMENT__PERSISTED_STATE: return BasicPackageImpl.DIALOG__PERSISTED_STATE;
				case ApplicationPackageImpl.APPLICATION_ELEMENT__TAGS: return BasicPackageImpl.DIALOG__TAGS;
				case ApplicationPackageImpl.APPLICATION_ELEMENT__CONTRIBUTOR_URI: return BasicPackageImpl.DIALOG__CONTRIBUTOR_URI;
				case ApplicationPackageImpl.APPLICATION_ELEMENT__TRANSIENT_DATA: return BasicPackageImpl.DIALOG__TRANSIENT_DATA;
				default: return -1;
			}
		}
		if (baseClass == MUIElement.class) {
			switch (baseFeatureID) {
				case UiPackageImpl.UI_ELEMENT__WIDGET: return BasicPackageImpl.DIALOG__WIDGET;
				case UiPackageImpl.UI_ELEMENT__RENDERER: return BasicPackageImpl.DIALOG__RENDERER;
				case UiPackageImpl.UI_ELEMENT__TO_BE_RENDERED: return BasicPackageImpl.DIALOG__TO_BE_RENDERED;
				case UiPackageImpl.UI_ELEMENT__ON_TOP: return BasicPackageImpl.DIALOG__ON_TOP;
				case UiPackageImpl.UI_ELEMENT__VISIBLE: return BasicPackageImpl.DIALOG__VISIBLE;
				case UiPackageImpl.UI_ELEMENT__PARENT: return BasicPackageImpl.DIALOG__PARENT;
				case UiPackageImpl.UI_ELEMENT__CONTAINER_DATA: return BasicPackageImpl.DIALOG__CONTAINER_DATA;
				case UiPackageImpl.UI_ELEMENT__CUR_SHARED_REF: return BasicPackageImpl.DIALOG__CUR_SHARED_REF;
				case UiPackageImpl.UI_ELEMENT__VISIBLE_WHEN: return BasicPackageImpl.DIALOG__VISIBLE_WHEN;
				case UiPackageImpl.UI_ELEMENT__ACCESSIBILITY_PHRASE: return BasicPackageImpl.DIALOG__ACCESSIBILITY_PHRASE;
				case UiPackageImpl.UI_ELEMENT__LOCALIZED_ACCESSIBILITY_PHRASE: return BasicPackageImpl.DIALOG__LOCALIZED_ACCESSIBILITY_PHRASE;
				default: return -1;
			}
		}
		if (baseClass == MElementContainer.class) {
			switch (baseFeatureID) {
				case UiPackageImpl.ELEMENT_CONTAINER__CHILDREN: return BasicPackageImpl.DIALOG__CHILDREN;
				case UiPackageImpl.ELEMENT_CONTAINER__SELECTED_ELEMENT: return BasicPackageImpl.DIALOG__SELECTED_ELEMENT;
				default: return -1;
			}
		}
		if (baseClass == MGenericTile.class) {
			switch (baseFeatureID) {
				case UiPackageImpl.GENERIC_TILE__HORIZONTAL: return BasicPackageImpl.DIALOG__HORIZONTAL;
				default: return -1;
			}
		}
		if (baseClass == MFrameElement.class) {
			switch (baseFeatureID) {
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
		result.append(", elementId: "); //$NON-NLS-1$
		result.append(elementId);
		result.append(", tags: "); //$NON-NLS-1$
		result.append(tags);
		result.append(", contributorURI: "); //$NON-NLS-1$
		result.append(contributorURI);
		result.append(", widget: "); //$NON-NLS-1$
		result.append(widget);
		result.append(", renderer: "); //$NON-NLS-1$
		result.append(renderer);
		result.append(", toBeRendered: "); //$NON-NLS-1$
		result.append(toBeRendered);
		result.append(", onTop: "); //$NON-NLS-1$
		result.append(onTop);
		result.append(", visible: "); //$NON-NLS-1$
		result.append(visible);
		result.append(", containerData: "); //$NON-NLS-1$
		result.append(containerData);
		result.append(", accessibilityPhrase: "); //$NON-NLS-1$
		result.append(accessibilityPhrase);
		result.append(", horizontal: "); //$NON-NLS-1$
		result.append(horizontal);
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

