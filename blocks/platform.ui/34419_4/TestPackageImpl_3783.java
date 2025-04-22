package org.eclipse.e4.ui.tests.model.test.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.MContribution;
import org.eclipse.e4.ui.model.application.commands.MCategory;
import org.eclipse.e4.ui.model.application.commands.MCommand;
import org.eclipse.e4.ui.model.application.commands.MCommandParameter;
import org.eclipse.e4.ui.model.application.commands.MParameter;
import org.eclipse.e4.ui.model.application.commands.impl.CommandsPackageImpl;
import org.eclipse.e4.ui.model.application.impl.ApplicationElementImpl;
import org.eclipse.e4.ui.model.application.impl.ApplicationPackageImpl;
import org.eclipse.e4.ui.model.application.impl.StringToStringMapImpl;
import org.eclipse.e4.ui.model.application.ui.MContext;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MExpression;
import org.eclipse.e4.ui.model.application.ui.MInput;
import org.eclipse.e4.ui.model.application.ui.MLocalizable;
import org.eclipse.e4.ui.model.application.ui.MSnippetContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.MUILabel;
import org.eclipse.e4.ui.model.application.ui.advanced.MPlaceholder;
import org.eclipse.e4.ui.model.application.ui.impl.UiPackageImpl;
import org.eclipse.e4.ui.tests.model.test.MTestHarness;
import org.eclipse.e4.ui.tests.model.test.MTestPackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

public class TestHarnessImpl extends ApplicationElementImpl implements
		MTestHarness {
	protected static final String COMMAND_NAME_EDEFAULT = null;
	protected String commandName = COMMAND_NAME_EDEFAULT;
	protected static final String DESCRIPTION_EDEFAULT = null;
	protected String description = DESCRIPTION_EDEFAULT;
	protected EList<MCommandParameter> parameters;
	protected MCategory category;
	protected static final String LOCALIZED_COMMAND_NAME_EDEFAULT = null;
	protected static final String LOCALIZED_DESCRIPTION_EDEFAULT = null;
	protected static final IEclipseContext CONTEXT_EDEFAULT = null;
	protected IEclipseContext context = CONTEXT_EDEFAULT;
	protected EList<String> variables;
	protected EMap<String, String> properties;
	protected static final String CONTRIBUTION_URI_EDEFAULT = null;
	protected String contributionURI = CONTRIBUTION_URI_EDEFAULT;
	protected static final Object OBJECT_EDEFAULT = null;
	protected Object object = OBJECT_EDEFAULT;
	protected static final Object WIDGET_EDEFAULT = null;
	protected Object widget = WIDGET_EDEFAULT;
	protected static final Object RENDERER_EDEFAULT = null;
	protected Object renderer = RENDERER_EDEFAULT;
	protected static final boolean TO_BE_RENDERED_EDEFAULT = true;
	protected boolean toBeRendered = TO_BE_RENDERED_EDEFAULT;
	protected static final boolean ON_TOP_EDEFAULT = false;
	protected boolean onTop = ON_TOP_EDEFAULT;
	protected static final boolean VISIBLE_EDEFAULT = true;
	protected boolean visible = VISIBLE_EDEFAULT;
	protected static final String CONTAINER_DATA_EDEFAULT = null;
	protected String containerData = CONTAINER_DATA_EDEFAULT;
	protected MPlaceholder curSharedRef;
	protected MExpression visibleWhen;
	protected static final String ACCESSIBILITY_PHRASE_EDEFAULT = null;
	protected String accessibilityPhrase = ACCESSIBILITY_PHRASE_EDEFAULT;
	protected static final String LOCALIZED_ACCESSIBILITY_PHRASE_EDEFAULT = null;
	protected EList<MUIElement> children;
	protected MUIElement selectedElement;
	protected static final String NAME_EDEFAULT = null;
	protected String name = NAME_EDEFAULT;
	protected static final String VALUE_EDEFAULT = null;
	protected String value = VALUE_EDEFAULT;
	protected static final String INPUT_URI_EDEFAULT = null;
	protected String inputURI = INPUT_URI_EDEFAULT;
	protected static final String LABEL_EDEFAULT = null;
	protected String label = LABEL_EDEFAULT;
	protected static final String ICON_URI_EDEFAULT = null;
	protected String iconURI = ICON_URI_EDEFAULT;
	protected static final String TOOLTIP_EDEFAULT = null;
	protected String tooltip = TOOLTIP_EDEFAULT;
	protected static final String LOCALIZED_LABEL_EDEFAULT = "";
	protected static final String LOCALIZED_TOOLTIP_EDEFAULT = "";
	protected static final boolean DIRTY_EDEFAULT = false;
	protected boolean dirty = DIRTY_EDEFAULT;

	protected EList<MUIElement> snippets;

	protected TestHarnessImpl() {
		super();
	}

	@Override
	protected EClass eStaticClass() {
		return MTestPackage.Literals.TEST_HARNESS;
	}

	public String getCommandName() {
		return commandName;
	}

	public void setCommandName(String newCommandName) {
		String oldCommandName = commandName;
		commandName = newCommandName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MTestPackage.TEST_HARNESS__COMMAND_NAME, oldCommandName, commandName));
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String newDescription) {
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MTestPackage.TEST_HARNESS__DESCRIPTION, oldDescription, description));
	}

	public List<MCommandParameter> getParameters() {
		if (parameters == null) {
			parameters = new EObjectContainmentEList<MCommandParameter>(MCommandParameter.class, this, MTestPackage.TEST_HARNESS__PARAMETERS);
		}
		return parameters;
	}

	public MCategory getCategory() {
		if (category != null && ((EObject)category).eIsProxy()) {
			InternalEObject oldCategory = (InternalEObject)category;
			category = (MCategory)eResolveProxy(oldCategory);
			if (category != oldCategory) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MTestPackage.TEST_HARNESS__CATEGORY, oldCategory, category));
			}
		}
		return category;
	}

	public MCategory basicGetCategory() {
		return category;
	}

	public void setCategory(MCategory newCategory) {
		MCategory oldCategory = category;
		category = newCategory;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MTestPackage.TEST_HARNESS__CATEGORY, oldCategory, category));
	}

	public IEclipseContext getContext() {
		return context;
	}

	public void setContext(IEclipseContext newContext) {
		IEclipseContext oldContext = context;
		context = newContext;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MTestPackage.TEST_HARNESS__CONTEXT, oldContext, context));
	}

	public List<String> getVariables() {
		if (variables == null) {
			variables = new EDataTypeUniqueEList<String>(String.class, this, MTestPackage.TEST_HARNESS__VARIABLES);
		}
		return variables;
	}

	public Map<String, String> getProperties() {
		if (properties == null) {
			properties = new EcoreEMap<String,String>(ApplicationPackageImpl.Literals.STRING_TO_STRING_MAP, StringToStringMapImpl.class, this, MTestPackage.TEST_HARNESS__PROPERTIES);
		}
		return properties.map();
	}

	public String getContributionURI() {
		return contributionURI;
	}

	public void setContributionURI(String newContributionURI) {
		String oldContributionURI = contributionURI;
		contributionURI = newContributionURI;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MTestPackage.TEST_HARNESS__CONTRIBUTION_URI, oldContributionURI, contributionURI));
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object newObject) {
		Object oldObject = object;
		object = newObject;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MTestPackage.TEST_HARNESS__OBJECT, oldObject, object));
	}

	public Object getWidget() {
		return widget;
	}

	public void setWidget(Object newWidget) {
		Object oldWidget = widget;
		widget = newWidget;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MTestPackage.TEST_HARNESS__WIDGET, oldWidget, widget));
	}

	public Object getRenderer() {
		return renderer;
	}

	public void setRenderer(Object newRenderer) {
		Object oldRenderer = renderer;
		renderer = newRenderer;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MTestPackage.TEST_HARNESS__RENDERER, oldRenderer, renderer));
	}

	public boolean isToBeRendered() {
		return toBeRendered;
	}

	public void setToBeRendered(boolean newToBeRendered) {
		boolean oldToBeRendered = toBeRendered;
		toBeRendered = newToBeRendered;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MTestPackage.TEST_HARNESS__TO_BE_RENDERED, oldToBeRendered, toBeRendered));
	}

	public boolean isOnTop() {
		return onTop;
	}

	public void setOnTop(boolean newOnTop) {
		boolean oldOnTop = onTop;
		onTop = newOnTop;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MTestPackage.TEST_HARNESS__ON_TOP, oldOnTop, onTop));
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean newVisible) {
		boolean oldVisible = visible;
		visible = newVisible;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MTestPackage.TEST_HARNESS__VISIBLE, oldVisible, visible));
	}

	@SuppressWarnings("unchecked")
	public MElementContainer<MUIElement> getParent() {
		if (eContainerFeatureID() != MTestPackage.TEST_HARNESS__PARENT) return null;
		return (MElementContainer<MUIElement>)eInternalContainer();
	}

	public NotificationChain basicSetParent(
			MElementContainer<MUIElement> newParent, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newParent, MTestPackage.TEST_HARNESS__PARENT, msgs);
		return msgs;
	}

	public void setParent(MElementContainer<MUIElement> newParent) {
		if (newParent != eInternalContainer() || (eContainerFeatureID() != MTestPackage.TEST_HARNESS__PARENT && newParent != null)) {
			if (EcoreUtil.isAncestor(this, (EObject)newParent))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newParent != null)
				msgs = ((InternalEObject)newParent).eInverseAdd(this, UiPackageImpl.ELEMENT_CONTAINER__CHILDREN, MElementContainer.class, msgs);
			msgs = basicSetParent(newParent, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MTestPackage.TEST_HARNESS__PARENT, newParent, newParent));
	}

	public String getContainerData() {
		return containerData;
	}

	public void setContainerData(String newContainerData) {
		String oldContainerData = containerData;
		containerData = newContainerData;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MTestPackage.TEST_HARNESS__CONTAINER_DATA, oldContainerData, containerData));
	}

	public MPlaceholder getCurSharedRef() {
		if (curSharedRef != null && ((EObject)curSharedRef).eIsProxy()) {
			InternalEObject oldCurSharedRef = (InternalEObject)curSharedRef;
			curSharedRef = (MPlaceholder)eResolveProxy(oldCurSharedRef);
			if (curSharedRef != oldCurSharedRef) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MTestPackage.TEST_HARNESS__CUR_SHARED_REF, oldCurSharedRef, curSharedRef));
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
			eNotify(new ENotificationImpl(this, Notification.SET, MTestPackage.TEST_HARNESS__CUR_SHARED_REF, oldCurSharedRef, curSharedRef));
	}

	public MExpression getVisibleWhen() {
		return visibleWhen;
	}

	public NotificationChain basicSetVisibleWhen(MExpression newVisibleWhen,
			NotificationChain msgs) {
		MExpression oldVisibleWhen = visibleWhen;
		visibleWhen = newVisibleWhen;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MTestPackage.TEST_HARNESS__VISIBLE_WHEN, oldVisibleWhen, newVisibleWhen);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	public void setVisibleWhen(MExpression newVisibleWhen) {
		if (newVisibleWhen != visibleWhen) {
			NotificationChain msgs = null;
			if (visibleWhen != null)
				msgs = ((InternalEObject)visibleWhen).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MTestPackage.TEST_HARNESS__VISIBLE_WHEN, null, msgs);
			if (newVisibleWhen != null)
				msgs = ((InternalEObject)newVisibleWhen).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MTestPackage.TEST_HARNESS__VISIBLE_WHEN, null, msgs);
			msgs = basicSetVisibleWhen(newVisibleWhen, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MTestPackage.TEST_HARNESS__VISIBLE_WHEN, newVisibleWhen, newVisibleWhen));
	}

	public String getAccessibilityPhrase() {
		return accessibilityPhrase;
	}

	public void setAccessibilityPhrase(String newAccessibilityPhrase) {
		String oldAccessibilityPhrase = accessibilityPhrase;
		accessibilityPhrase = newAccessibilityPhrase;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MTestPackage.TEST_HARNESS__ACCESSIBILITY_PHRASE, oldAccessibilityPhrase, accessibilityPhrase));
	}

	public List<MUIElement> getChildren() {
		if (children == null) {
			children = new EObjectContainmentWithInverseEList<MUIElement>(MUIElement.class, this, MTestPackage.TEST_HARNESS__CHILDREN, UiPackageImpl.UI_ELEMENT__PARENT);
		}
		return children;
	}

	public MUIElement getSelectedElement() {
		if (selectedElement != null && ((EObject)selectedElement).eIsProxy()) {
			InternalEObject oldSelectedElement = (InternalEObject)selectedElement;
			selectedElement = (MUIElement)eResolveProxy(oldSelectedElement);
			if (selectedElement != oldSelectedElement) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MTestPackage.TEST_HARNESS__SELECTED_ELEMENT, oldSelectedElement, selectedElement));
			}
		}
		return selectedElement;
	}

	public MUIElement basicGetSelectedElement() {
		return selectedElement;
	}

	public void setSelectedElement(MUIElement newSelectedElement) {
		MUIElement oldSelectedElement = selectedElement;
		selectedElement = newSelectedElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MTestPackage.TEST_HARNESS__SELECTED_ELEMENT, oldSelectedElement, selectedElement));
	}

	public String getName() {
		return name;
	}

	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MTestPackage.TEST_HARNESS__NAME, oldName, name));
	}

	public String getValue() {
		return value;
	}

	public void setValue(String newValue) {
		String oldValue = value;
		value = newValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MTestPackage.TEST_HARNESS__VALUE, oldValue, value));
	}

	public String getInputURI() {
		return inputURI;
	}

	public void setInputURI(String newInputURI) {
		String oldInputURI = inputURI;
		inputURI = newInputURI;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MTestPackage.TEST_HARNESS__INPUT_URI, oldInputURI, inputURI));
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String newLabel) {
		String oldLabel = label;
		label = newLabel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MTestPackage.TEST_HARNESS__LABEL, oldLabel, label));
	}

	public String getIconURI() {
		return iconURI;
	}

	public void setIconURI(String newIconURI) {
		String oldIconURI = iconURI;
		iconURI = newIconURI;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MTestPackage.TEST_HARNESS__ICON_URI, oldIconURI, iconURI));
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String newTooltip) {
		String oldTooltip = tooltip;
		tooltip = newTooltip;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MTestPackage.TEST_HARNESS__TOOLTIP, oldTooltip, tooltip));
	}

	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(boolean newDirty) {
		boolean oldDirty = dirty;
		dirty = newDirty;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MTestPackage.TEST_HARNESS__DIRTY, oldDirty, dirty));
	}

	public List<MUIElement> getSnippets() {
		if (snippets == null) {
			snippets = new EObjectContainmentEList<MUIElement>(MUIElement.class, this, MTestPackage.TEST_HARNESS__SNIPPETS);
		}
		return snippets;
	}

	public void updateLocalization() {
		throw new UnsupportedOperationException();
	}

	public String getLocalizedLabel() {
		throw new UnsupportedOperationException();
	}

	public String getLocalizedTooltip() {
		throw new UnsupportedOperationException();
	}

	public String getLocalizedAccessibilityPhrase() {
		throw new UnsupportedOperationException();
	}

	public String getLocalizedCommandName() {
		throw new UnsupportedOperationException();
	}

	public String getLocalizedDescription() {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MTestPackage.TEST_HARNESS__PARENT:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetParent((MElementContainer<MUIElement>)otherEnd, msgs);
			case MTestPackage.TEST_HARNESS__CHILDREN:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getChildren()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MTestPackage.TEST_HARNESS__PARAMETERS:
				return ((InternalEList<?>)getParameters()).basicRemove(otherEnd, msgs);
			case MTestPackage.TEST_HARNESS__PROPERTIES:
				return ((InternalEList<?>)((EMap.InternalMapView<String, String>)getProperties()).eMap()).basicRemove(otherEnd, msgs);
			case MTestPackage.TEST_HARNESS__PARENT:
				return basicSetParent(null, msgs);
			case MTestPackage.TEST_HARNESS__VISIBLE_WHEN:
				return basicSetVisibleWhen(null, msgs);
			case MTestPackage.TEST_HARNESS__CHILDREN:
				return ((InternalEList<?>)getChildren()).basicRemove(otherEnd, msgs);
			case MTestPackage.TEST_HARNESS__SNIPPETS:
				return ((InternalEList<?>)getSnippets()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(
			NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case MTestPackage.TEST_HARNESS__PARENT:
				return eInternalContainer().eInverseRemove(this, UiPackageImpl.ELEMENT_CONTAINER__CHILDREN, MElementContainer.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MTestPackage.TEST_HARNESS__COMMAND_NAME:
				return getCommandName();
			case MTestPackage.TEST_HARNESS__DESCRIPTION:
				return getDescription();
			case MTestPackage.TEST_HARNESS__PARAMETERS:
				return getParameters();
			case MTestPackage.TEST_HARNESS__CATEGORY:
				if (resolve) return getCategory();
				return basicGetCategory();
			case MTestPackage.TEST_HARNESS__LOCALIZED_COMMAND_NAME:
				return getLocalizedCommandName();
			case MTestPackage.TEST_HARNESS__LOCALIZED_DESCRIPTION:
				return getLocalizedDescription();
			case MTestPackage.TEST_HARNESS__CONTEXT:
				return getContext();
			case MTestPackage.TEST_HARNESS__VARIABLES:
				return getVariables();
			case MTestPackage.TEST_HARNESS__PROPERTIES:
				if (coreType) return ((EMap.InternalMapView<String, String>)getProperties()).eMap();
				else return getProperties();
			case MTestPackage.TEST_HARNESS__CONTRIBUTION_URI:
				return getContributionURI();
			case MTestPackage.TEST_HARNESS__OBJECT:
				return getObject();
			case MTestPackage.TEST_HARNESS__WIDGET:
				return getWidget();
			case MTestPackage.TEST_HARNESS__RENDERER:
				return getRenderer();
			case MTestPackage.TEST_HARNESS__TO_BE_RENDERED:
				return isToBeRendered();
			case MTestPackage.TEST_HARNESS__ON_TOP:
				return isOnTop();
			case MTestPackage.TEST_HARNESS__VISIBLE:
				return isVisible();
			case MTestPackage.TEST_HARNESS__PARENT:
				return getParent();
			case MTestPackage.TEST_HARNESS__CONTAINER_DATA:
				return getContainerData();
			case MTestPackage.TEST_HARNESS__CUR_SHARED_REF:
				if (resolve) return getCurSharedRef();
				return basicGetCurSharedRef();
			case MTestPackage.TEST_HARNESS__VISIBLE_WHEN:
				return getVisibleWhen();
			case MTestPackage.TEST_HARNESS__ACCESSIBILITY_PHRASE:
				return getAccessibilityPhrase();
			case MTestPackage.TEST_HARNESS__LOCALIZED_ACCESSIBILITY_PHRASE:
				return getLocalizedAccessibilityPhrase();
			case MTestPackage.TEST_HARNESS__CHILDREN:
				return getChildren();
			case MTestPackage.TEST_HARNESS__SELECTED_ELEMENT:
				if (resolve) return getSelectedElement();
				return basicGetSelectedElement();
			case MTestPackage.TEST_HARNESS__NAME:
				return getName();
			case MTestPackage.TEST_HARNESS__VALUE:
				return getValue();
			case MTestPackage.TEST_HARNESS__INPUT_URI:
				return getInputURI();
			case MTestPackage.TEST_HARNESS__LABEL:
				return getLabel();
			case MTestPackage.TEST_HARNESS__ICON_URI:
				return getIconURI();
			case MTestPackage.TEST_HARNESS__TOOLTIP:
				return getTooltip();
			case MTestPackage.TEST_HARNESS__LOCALIZED_LABEL:
				return getLocalizedLabel();
			case MTestPackage.TEST_HARNESS__LOCALIZED_TOOLTIP:
				return getLocalizedTooltip();
			case MTestPackage.TEST_HARNESS__DIRTY:
				return isDirty();
			case MTestPackage.TEST_HARNESS__SNIPPETS:
				return getSnippets();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MTestPackage.TEST_HARNESS__COMMAND_NAME:
				setCommandName((String)newValue);
				return;
			case MTestPackage.TEST_HARNESS__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case MTestPackage.TEST_HARNESS__PARAMETERS:
				getParameters().clear();
				getParameters().addAll((Collection<? extends MCommandParameter>)newValue);
				return;
			case MTestPackage.TEST_HARNESS__CATEGORY:
				setCategory((MCategory)newValue);
				return;
			case MTestPackage.TEST_HARNESS__CONTEXT:
				setContext((IEclipseContext)newValue);
				return;
			case MTestPackage.TEST_HARNESS__VARIABLES:
				getVariables().clear();
				getVariables().addAll((Collection<? extends String>)newValue);
				return;
			case MTestPackage.TEST_HARNESS__PROPERTIES:
				((EStructuralFeature.Setting)((EMap.InternalMapView<String, String>)getProperties()).eMap()).set(newValue);
				return;
			case MTestPackage.TEST_HARNESS__CONTRIBUTION_URI:
				setContributionURI((String)newValue);
				return;
			case MTestPackage.TEST_HARNESS__OBJECT:
				setObject(newValue);
				return;
			case MTestPackage.TEST_HARNESS__WIDGET:
				setWidget(newValue);
				return;
			case MTestPackage.TEST_HARNESS__RENDERER:
				setRenderer(newValue);
				return;
			case MTestPackage.TEST_HARNESS__TO_BE_RENDERED:
				setToBeRendered((Boolean)newValue);
				return;
			case MTestPackage.TEST_HARNESS__ON_TOP:
				setOnTop((Boolean)newValue);
				return;
			case MTestPackage.TEST_HARNESS__VISIBLE:
				setVisible((Boolean)newValue);
				return;
			case MTestPackage.TEST_HARNESS__PARENT:
				setParent((MElementContainer<MUIElement>)newValue);
				return;
			case MTestPackage.TEST_HARNESS__CONTAINER_DATA:
				setContainerData((String)newValue);
				return;
			case MTestPackage.TEST_HARNESS__CUR_SHARED_REF:
				setCurSharedRef((MPlaceholder)newValue);
				return;
			case MTestPackage.TEST_HARNESS__VISIBLE_WHEN:
				setVisibleWhen((MExpression)newValue);
				return;
			case MTestPackage.TEST_HARNESS__ACCESSIBILITY_PHRASE:
				setAccessibilityPhrase((String)newValue);
				return;
			case MTestPackage.TEST_HARNESS__CHILDREN:
				getChildren().clear();
				getChildren().addAll((Collection<? extends MUIElement>)newValue);
				return;
			case MTestPackage.TEST_HARNESS__SELECTED_ELEMENT:
				setSelectedElement((MUIElement)newValue);
				return;
			case MTestPackage.TEST_HARNESS__NAME:
				setName((String)newValue);
				return;
			case MTestPackage.TEST_HARNESS__VALUE:
				setValue((String)newValue);
				return;
			case MTestPackage.TEST_HARNESS__INPUT_URI:
				setInputURI((String)newValue);
				return;
			case MTestPackage.TEST_HARNESS__LABEL:
				setLabel((String)newValue);
				return;
			case MTestPackage.TEST_HARNESS__ICON_URI:
				setIconURI((String)newValue);
				return;
			case MTestPackage.TEST_HARNESS__TOOLTIP:
				setTooltip((String)newValue);
				return;
			case MTestPackage.TEST_HARNESS__DIRTY:
				setDirty((Boolean)newValue);
				return;
			case MTestPackage.TEST_HARNESS__SNIPPETS:
				getSnippets().clear();
				getSnippets().addAll((Collection<? extends MUIElement>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case MTestPackage.TEST_HARNESS__COMMAND_NAME:
				setCommandName(COMMAND_NAME_EDEFAULT);
				return;
			case MTestPackage.TEST_HARNESS__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case MTestPackage.TEST_HARNESS__PARAMETERS:
				getParameters().clear();
				return;
			case MTestPackage.TEST_HARNESS__CATEGORY:
				setCategory((MCategory)null);
				return;
			case MTestPackage.TEST_HARNESS__CONTEXT:
				setContext(CONTEXT_EDEFAULT);
				return;
			case MTestPackage.TEST_HARNESS__VARIABLES:
				getVariables().clear();
				return;
			case MTestPackage.TEST_HARNESS__PROPERTIES:
				getProperties().clear();
				return;
			case MTestPackage.TEST_HARNESS__CONTRIBUTION_URI:
				setContributionURI(CONTRIBUTION_URI_EDEFAULT);
				return;
			case MTestPackage.TEST_HARNESS__OBJECT:
				setObject(OBJECT_EDEFAULT);
				return;
			case MTestPackage.TEST_HARNESS__WIDGET:
				setWidget(WIDGET_EDEFAULT);
				return;
			case MTestPackage.TEST_HARNESS__RENDERER:
				setRenderer(RENDERER_EDEFAULT);
				return;
			case MTestPackage.TEST_HARNESS__TO_BE_RENDERED:
				setToBeRendered(TO_BE_RENDERED_EDEFAULT);
				return;
			case MTestPackage.TEST_HARNESS__ON_TOP:
				setOnTop(ON_TOP_EDEFAULT);
				return;
			case MTestPackage.TEST_HARNESS__VISIBLE:
				setVisible(VISIBLE_EDEFAULT);
				return;
			case MTestPackage.TEST_HARNESS__PARENT:
				setParent((MElementContainer<MUIElement>)null);
				return;
			case MTestPackage.TEST_HARNESS__CONTAINER_DATA:
				setContainerData(CONTAINER_DATA_EDEFAULT);
				return;
			case MTestPackage.TEST_HARNESS__CUR_SHARED_REF:
				setCurSharedRef((MPlaceholder)null);
				return;
			case MTestPackage.TEST_HARNESS__VISIBLE_WHEN:
				setVisibleWhen((MExpression)null);
				return;
			case MTestPackage.TEST_HARNESS__ACCESSIBILITY_PHRASE:
				setAccessibilityPhrase(ACCESSIBILITY_PHRASE_EDEFAULT);
				return;
			case MTestPackage.TEST_HARNESS__CHILDREN:
				getChildren().clear();
				return;
			case MTestPackage.TEST_HARNESS__SELECTED_ELEMENT:
				setSelectedElement((MUIElement)null);
				return;
			case MTestPackage.TEST_HARNESS__NAME:
				setName(NAME_EDEFAULT);
				return;
			case MTestPackage.TEST_HARNESS__VALUE:
				setValue(VALUE_EDEFAULT);
				return;
			case MTestPackage.TEST_HARNESS__INPUT_URI:
				setInputURI(INPUT_URI_EDEFAULT);
				return;
			case MTestPackage.TEST_HARNESS__LABEL:
				setLabel(LABEL_EDEFAULT);
				return;
			case MTestPackage.TEST_HARNESS__ICON_URI:
				setIconURI(ICON_URI_EDEFAULT);
				return;
			case MTestPackage.TEST_HARNESS__TOOLTIP:
				setTooltip(TOOLTIP_EDEFAULT);
				return;
			case MTestPackage.TEST_HARNESS__DIRTY:
				setDirty(DIRTY_EDEFAULT);
				return;
			case MTestPackage.TEST_HARNESS__SNIPPETS:
				getSnippets().clear();
				return;
		}
		super.eUnset(featureID);
	}

	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case MTestPackage.TEST_HARNESS__COMMAND_NAME:
				return COMMAND_NAME_EDEFAULT == null ? commandName != null : !COMMAND_NAME_EDEFAULT.equals(commandName);
			case MTestPackage.TEST_HARNESS__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case MTestPackage.TEST_HARNESS__PARAMETERS:
				return parameters != null && !parameters.isEmpty();
			case MTestPackage.TEST_HARNESS__CATEGORY:
				return category != null;
			case MTestPackage.TEST_HARNESS__LOCALIZED_COMMAND_NAME:
				return LOCALIZED_COMMAND_NAME_EDEFAULT == null ? getLocalizedCommandName() != null : !LOCALIZED_COMMAND_NAME_EDEFAULT.equals(getLocalizedCommandName());
			case MTestPackage.TEST_HARNESS__LOCALIZED_DESCRIPTION:
				return LOCALIZED_DESCRIPTION_EDEFAULT == null ? getLocalizedDescription() != null : !LOCALIZED_DESCRIPTION_EDEFAULT.equals(getLocalizedDescription());
			case MTestPackage.TEST_HARNESS__CONTEXT:
				return CONTEXT_EDEFAULT == null ? context != null : !CONTEXT_EDEFAULT.equals(context);
			case MTestPackage.TEST_HARNESS__VARIABLES:
				return variables != null && !variables.isEmpty();
			case MTestPackage.TEST_HARNESS__PROPERTIES:
				return properties != null && !properties.isEmpty();
			case MTestPackage.TEST_HARNESS__CONTRIBUTION_URI:
				return CONTRIBUTION_URI_EDEFAULT == null ? contributionURI != null : !CONTRIBUTION_URI_EDEFAULT.equals(contributionURI);
			case MTestPackage.TEST_HARNESS__OBJECT:
				return OBJECT_EDEFAULT == null ? object != null : !OBJECT_EDEFAULT.equals(object);
			case MTestPackage.TEST_HARNESS__WIDGET:
				return WIDGET_EDEFAULT == null ? widget != null : !WIDGET_EDEFAULT.equals(widget);
			case MTestPackage.TEST_HARNESS__RENDERER:
				return RENDERER_EDEFAULT == null ? renderer != null : !RENDERER_EDEFAULT.equals(renderer);
			case MTestPackage.TEST_HARNESS__TO_BE_RENDERED:
				return toBeRendered != TO_BE_RENDERED_EDEFAULT;
			case MTestPackage.TEST_HARNESS__ON_TOP:
				return onTop != ON_TOP_EDEFAULT;
			case MTestPackage.TEST_HARNESS__VISIBLE:
				return visible != VISIBLE_EDEFAULT;
			case MTestPackage.TEST_HARNESS__PARENT:
				return getParent() != null;
			case MTestPackage.TEST_HARNESS__CONTAINER_DATA:
				return CONTAINER_DATA_EDEFAULT == null ? containerData != null : !CONTAINER_DATA_EDEFAULT.equals(containerData);
			case MTestPackage.TEST_HARNESS__CUR_SHARED_REF:
				return curSharedRef != null;
			case MTestPackage.TEST_HARNESS__VISIBLE_WHEN:
				return visibleWhen != null;
			case MTestPackage.TEST_HARNESS__ACCESSIBILITY_PHRASE:
				return ACCESSIBILITY_PHRASE_EDEFAULT == null ? accessibilityPhrase != null : !ACCESSIBILITY_PHRASE_EDEFAULT.equals(accessibilityPhrase);
			case MTestPackage.TEST_HARNESS__LOCALIZED_ACCESSIBILITY_PHRASE:
				return LOCALIZED_ACCESSIBILITY_PHRASE_EDEFAULT == null ? getLocalizedAccessibilityPhrase() != null : !LOCALIZED_ACCESSIBILITY_PHRASE_EDEFAULT.equals(getLocalizedAccessibilityPhrase());
			case MTestPackage.TEST_HARNESS__CHILDREN:
				return children != null && !children.isEmpty();
			case MTestPackage.TEST_HARNESS__SELECTED_ELEMENT:
				return selectedElement != null;
			case MTestPackage.TEST_HARNESS__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case MTestPackage.TEST_HARNESS__VALUE:
				return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT.equals(value);
			case MTestPackage.TEST_HARNESS__INPUT_URI:
				return INPUT_URI_EDEFAULT == null ? inputURI != null : !INPUT_URI_EDEFAULT.equals(inputURI);
			case MTestPackage.TEST_HARNESS__LABEL:
				return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
			case MTestPackage.TEST_HARNESS__ICON_URI:
				return ICON_URI_EDEFAULT == null ? iconURI != null : !ICON_URI_EDEFAULT.equals(iconURI);
			case MTestPackage.TEST_HARNESS__TOOLTIP:
				return TOOLTIP_EDEFAULT == null ? tooltip != null : !TOOLTIP_EDEFAULT.equals(tooltip);
			case MTestPackage.TEST_HARNESS__LOCALIZED_LABEL:
				return LOCALIZED_LABEL_EDEFAULT == null ? getLocalizedLabel() != null : !LOCALIZED_LABEL_EDEFAULT.equals(getLocalizedLabel());
			case MTestPackage.TEST_HARNESS__LOCALIZED_TOOLTIP:
				return LOCALIZED_TOOLTIP_EDEFAULT == null ? getLocalizedTooltip() != null : !LOCALIZED_TOOLTIP_EDEFAULT.equals(getLocalizedTooltip());
			case MTestPackage.TEST_HARNESS__DIRTY:
				return dirty != DIRTY_EDEFAULT;
			case MTestPackage.TEST_HARNESS__SNIPPETS:
				return snippets != null && !snippets.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == MLocalizable.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == MCommand.class) {
			switch (derivedFeatureID) {
				case MTestPackage.TEST_HARNESS__COMMAND_NAME: return CommandsPackageImpl.COMMAND__COMMAND_NAME;
				case MTestPackage.TEST_HARNESS__DESCRIPTION: return CommandsPackageImpl.COMMAND__DESCRIPTION;
				case MTestPackage.TEST_HARNESS__PARAMETERS: return CommandsPackageImpl.COMMAND__PARAMETERS;
				case MTestPackage.TEST_HARNESS__CATEGORY: return CommandsPackageImpl.COMMAND__CATEGORY;
				case MTestPackage.TEST_HARNESS__LOCALIZED_COMMAND_NAME: return CommandsPackageImpl.COMMAND__LOCALIZED_COMMAND_NAME;
				case MTestPackage.TEST_HARNESS__LOCALIZED_DESCRIPTION: return CommandsPackageImpl.COMMAND__LOCALIZED_DESCRIPTION;
				default: return -1;
			}
		}
		if (baseClass == MContext.class) {
			switch (derivedFeatureID) {
				case MTestPackage.TEST_HARNESS__CONTEXT: return UiPackageImpl.CONTEXT__CONTEXT;
				case MTestPackage.TEST_HARNESS__VARIABLES: return UiPackageImpl.CONTEXT__VARIABLES;
				case MTestPackage.TEST_HARNESS__PROPERTIES: return UiPackageImpl.CONTEXT__PROPERTIES;
				default: return -1;
			}
		}
		if (baseClass == MContribution.class) {
			switch (derivedFeatureID) {
				case MTestPackage.TEST_HARNESS__CONTRIBUTION_URI: return ApplicationPackageImpl.CONTRIBUTION__CONTRIBUTION_URI;
				case MTestPackage.TEST_HARNESS__OBJECT: return ApplicationPackageImpl.CONTRIBUTION__OBJECT;
				default: return -1;
			}
		}
		if (baseClass == MUIElement.class) {
			switch (derivedFeatureID) {
				case MTestPackage.TEST_HARNESS__WIDGET: return UiPackageImpl.UI_ELEMENT__WIDGET;
				case MTestPackage.TEST_HARNESS__RENDERER: return UiPackageImpl.UI_ELEMENT__RENDERER;
				case MTestPackage.TEST_HARNESS__TO_BE_RENDERED: return UiPackageImpl.UI_ELEMENT__TO_BE_RENDERED;
				case MTestPackage.TEST_HARNESS__ON_TOP: return UiPackageImpl.UI_ELEMENT__ON_TOP;
				case MTestPackage.TEST_HARNESS__VISIBLE: return UiPackageImpl.UI_ELEMENT__VISIBLE;
				case MTestPackage.TEST_HARNESS__PARENT: return UiPackageImpl.UI_ELEMENT__PARENT;
				case MTestPackage.TEST_HARNESS__CONTAINER_DATA: return UiPackageImpl.UI_ELEMENT__CONTAINER_DATA;
				case MTestPackage.TEST_HARNESS__CUR_SHARED_REF: return UiPackageImpl.UI_ELEMENT__CUR_SHARED_REF;
				case MTestPackage.TEST_HARNESS__VISIBLE_WHEN: return UiPackageImpl.UI_ELEMENT__VISIBLE_WHEN;
				case MTestPackage.TEST_HARNESS__ACCESSIBILITY_PHRASE: return UiPackageImpl.UI_ELEMENT__ACCESSIBILITY_PHRASE;
				case MTestPackage.TEST_HARNESS__LOCALIZED_ACCESSIBILITY_PHRASE: return UiPackageImpl.UI_ELEMENT__LOCALIZED_ACCESSIBILITY_PHRASE;
				default: return -1;
			}
		}
		if (baseClass == MElementContainer.class) {
			switch (derivedFeatureID) {
				case MTestPackage.TEST_HARNESS__CHILDREN: return UiPackageImpl.ELEMENT_CONTAINER__CHILDREN;
				case MTestPackage.TEST_HARNESS__SELECTED_ELEMENT: return UiPackageImpl.ELEMENT_CONTAINER__SELECTED_ELEMENT;
				default: return -1;
			}
		}
		if (baseClass == MParameter.class) {
			switch (derivedFeatureID) {
				case MTestPackage.TEST_HARNESS__NAME: return CommandsPackageImpl.PARAMETER__NAME;
				case MTestPackage.TEST_HARNESS__VALUE: return CommandsPackageImpl.PARAMETER__VALUE;
				default: return -1;
			}
		}
		if (baseClass == MInput.class) {
			switch (derivedFeatureID) {
				case MTestPackage.TEST_HARNESS__INPUT_URI: return UiPackageImpl.INPUT__INPUT_URI;
				default: return -1;
			}
		}
		if (baseClass == MUILabel.class) {
			switch (derivedFeatureID) {
				case MTestPackage.TEST_HARNESS__LABEL: return UiPackageImpl.UI_LABEL__LABEL;
				case MTestPackage.TEST_HARNESS__ICON_URI: return UiPackageImpl.UI_LABEL__ICON_URI;
				case MTestPackage.TEST_HARNESS__TOOLTIP: return UiPackageImpl.UI_LABEL__TOOLTIP;
				case MTestPackage.TEST_HARNESS__LOCALIZED_LABEL: return UiPackageImpl.UI_LABEL__LOCALIZED_LABEL;
				case MTestPackage.TEST_HARNESS__LOCALIZED_TOOLTIP: return UiPackageImpl.UI_LABEL__LOCALIZED_TOOLTIP;
				default: return -1;
			}
		}
		if (baseClass == MDirtyable.class) {
			switch (derivedFeatureID) {
				case MTestPackage.TEST_HARNESS__DIRTY: return UiPackageImpl.DIRTYABLE__DIRTY;
				default: return -1;
			}
		}
		if (baseClass == MSnippetContainer.class) {
			switch (derivedFeatureID) {
				case MTestPackage.TEST_HARNESS__SNIPPETS: return UiPackageImpl.SNIPPET_CONTAINER__SNIPPETS;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == MLocalizable.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == MCommand.class) {
			switch (baseFeatureID) {
				case CommandsPackageImpl.COMMAND__COMMAND_NAME: return MTestPackage.TEST_HARNESS__COMMAND_NAME;
				case CommandsPackageImpl.COMMAND__DESCRIPTION: return MTestPackage.TEST_HARNESS__DESCRIPTION;
				case CommandsPackageImpl.COMMAND__PARAMETERS: return MTestPackage.TEST_HARNESS__PARAMETERS;
				case CommandsPackageImpl.COMMAND__CATEGORY: return MTestPackage.TEST_HARNESS__CATEGORY;
				case CommandsPackageImpl.COMMAND__LOCALIZED_COMMAND_NAME: return MTestPackage.TEST_HARNESS__LOCALIZED_COMMAND_NAME;
				case CommandsPackageImpl.COMMAND__LOCALIZED_DESCRIPTION: return MTestPackage.TEST_HARNESS__LOCALIZED_DESCRIPTION;
				default: return -1;
			}
		}
		if (baseClass == MContext.class) {
			switch (baseFeatureID) {
				case UiPackageImpl.CONTEXT__CONTEXT: return MTestPackage.TEST_HARNESS__CONTEXT;
				case UiPackageImpl.CONTEXT__VARIABLES: return MTestPackage.TEST_HARNESS__VARIABLES;
				case UiPackageImpl.CONTEXT__PROPERTIES: return MTestPackage.TEST_HARNESS__PROPERTIES;
				default: return -1;
			}
		}
		if (baseClass == MContribution.class) {
			switch (baseFeatureID) {
				case ApplicationPackageImpl.CONTRIBUTION__CONTRIBUTION_URI: return MTestPackage.TEST_HARNESS__CONTRIBUTION_URI;
				case ApplicationPackageImpl.CONTRIBUTION__OBJECT: return MTestPackage.TEST_HARNESS__OBJECT;
				default: return -1;
			}
		}
		if (baseClass == MUIElement.class) {
			switch (baseFeatureID) {
				case UiPackageImpl.UI_ELEMENT__WIDGET: return MTestPackage.TEST_HARNESS__WIDGET;
				case UiPackageImpl.UI_ELEMENT__RENDERER: return MTestPackage.TEST_HARNESS__RENDERER;
				case UiPackageImpl.UI_ELEMENT__TO_BE_RENDERED: return MTestPackage.TEST_HARNESS__TO_BE_RENDERED;
				case UiPackageImpl.UI_ELEMENT__ON_TOP: return MTestPackage.TEST_HARNESS__ON_TOP;
				case UiPackageImpl.UI_ELEMENT__VISIBLE: return MTestPackage.TEST_HARNESS__VISIBLE;
				case UiPackageImpl.UI_ELEMENT__PARENT: return MTestPackage.TEST_HARNESS__PARENT;
				case UiPackageImpl.UI_ELEMENT__CONTAINER_DATA: return MTestPackage.TEST_HARNESS__CONTAINER_DATA;
				case UiPackageImpl.UI_ELEMENT__CUR_SHARED_REF: return MTestPackage.TEST_HARNESS__CUR_SHARED_REF;
				case UiPackageImpl.UI_ELEMENT__VISIBLE_WHEN: return MTestPackage.TEST_HARNESS__VISIBLE_WHEN;
				case UiPackageImpl.UI_ELEMENT__ACCESSIBILITY_PHRASE: return MTestPackage.TEST_HARNESS__ACCESSIBILITY_PHRASE;
				case UiPackageImpl.UI_ELEMENT__LOCALIZED_ACCESSIBILITY_PHRASE: return MTestPackage.TEST_HARNESS__LOCALIZED_ACCESSIBILITY_PHRASE;
				default: return -1;
			}
		}
		if (baseClass == MElementContainer.class) {
			switch (baseFeatureID) {
				case UiPackageImpl.ELEMENT_CONTAINER__CHILDREN: return MTestPackage.TEST_HARNESS__CHILDREN;
				case UiPackageImpl.ELEMENT_CONTAINER__SELECTED_ELEMENT: return MTestPackage.TEST_HARNESS__SELECTED_ELEMENT;
				default: return -1;
			}
		}
		if (baseClass == MParameter.class) {
			switch (baseFeatureID) {
				case CommandsPackageImpl.PARAMETER__NAME: return MTestPackage.TEST_HARNESS__NAME;
				case CommandsPackageImpl.PARAMETER__VALUE: return MTestPackage.TEST_HARNESS__VALUE;
				default: return -1;
			}
		}
		if (baseClass == MInput.class) {
			switch (baseFeatureID) {
				case UiPackageImpl.INPUT__INPUT_URI: return MTestPackage.TEST_HARNESS__INPUT_URI;
				default: return -1;
			}
		}
		if (baseClass == MUILabel.class) {
			switch (baseFeatureID) {
				case UiPackageImpl.UI_LABEL__LABEL: return MTestPackage.TEST_HARNESS__LABEL;
				case UiPackageImpl.UI_LABEL__ICON_URI: return MTestPackage.TEST_HARNESS__ICON_URI;
				case UiPackageImpl.UI_LABEL__TOOLTIP: return MTestPackage.TEST_HARNESS__TOOLTIP;
				case UiPackageImpl.UI_LABEL__LOCALIZED_LABEL: return MTestPackage.TEST_HARNESS__LOCALIZED_LABEL;
				case UiPackageImpl.UI_LABEL__LOCALIZED_TOOLTIP: return MTestPackage.TEST_HARNESS__LOCALIZED_TOOLTIP;
				default: return -1;
			}
		}
		if (baseClass == MDirtyable.class) {
			switch (baseFeatureID) {
				case UiPackageImpl.DIRTYABLE__DIRTY: return MTestPackage.TEST_HARNESS__DIRTY;
				default: return -1;
			}
		}
		if (baseClass == MSnippetContainer.class) {
			switch (baseFeatureID) {
				case UiPackageImpl.SNIPPET_CONTAINER__SNIPPETS: return MTestPackage.TEST_HARNESS__SNIPPETS;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	@Override
	public int eDerivedOperationID(int baseOperationID, Class<?> baseClass) {
		if (baseClass == MLocalizable.class) {
			switch (baseOperationID) {
				case UiPackageImpl.LOCALIZABLE___UPDATE_LOCALIZATION: return MTestPackage.TEST_HARNESS___UPDATE_LOCALIZATION;
				default: return -1;
			}
		}
		if (baseClass == MCommand.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == MContext.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == MContribution.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == MUIElement.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == MElementContainer.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == MParameter.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == MInput.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == MUILabel.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == MDirtyable.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == MSnippetContainer.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		return super.eDerivedOperationID(baseOperationID, baseClass);
	}

	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case MTestPackage.TEST_HARNESS___UPDATE_LOCALIZATION:
				updateLocalization();
				return null;
		}
		return super.eInvoke(operationID, arguments);
	}

	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (commandName: ");
		result.append(commandName);
		result.append(", description: ");
		result.append(description);
		result.append(", context: ");
		result.append(context);
		result.append(", variables: ");
		result.append(variables);
		result.append(", contributionURI: ");
		result.append(contributionURI);
		result.append(", object: ");
		result.append(object);
		result.append(", widget: ");
		result.append(widget);
		result.append(", renderer: ");
		result.append(renderer);
		result.append(", toBeRendered: ");
		result.append(toBeRendered);
		result.append(", onTop: ");
		result.append(onTop);
		result.append(", visible: ");
		result.append(visible);
		result.append(", containerData: ");
		result.append(containerData);
		result.append(", accessibilityPhrase: ");
		result.append(accessibilityPhrase);
		result.append(", name: ");
		result.append(name);
		result.append(", value: ");
		result.append(value);
		result.append(", inputURI: ");
		result.append(inputURI);
		result.append(", label: ");
		result.append(label);
		result.append(", iconURI: ");
		result.append(iconURI);
		result.append(", tooltip: ");
		result.append(tooltip);
		result.append(", dirty: ");
		result.append(dirty);
		result.append(')');
		return result.toString();
	}

	public String getLocalLabel() {
		return null;
	}

	public void setLocalLabel(String value) {

	}

	public String getLocalTooltip() {
		return null;
	}

	public void setLocalTooltip(String value) {

	}

	public Object getLocalImage() {
		return null;
	}

	public void setLocalImage(Object value) {

	}

} // TestHarnessImpl
