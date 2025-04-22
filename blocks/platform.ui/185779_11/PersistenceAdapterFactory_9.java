package org.eclipse.e4.ui.workbench.internal.persistence.impl;

import java.util.Collection;

import org.eclipse.e4.ui.model.application.ui.advanced.MArea;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;

import org.eclipse.e4.ui.model.application.ui.basic.MTrimBar;

import org.eclipse.e4.ui.workbench.internal.persistence.IPartMemento;
import org.eclipse.e4.ui.workbench.internal.persistence.IPersistencePackage;
import org.eclipse.e4.ui.workbench.internal.persistence.IWorkbenchState;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

public class WorkbenchState extends MinimalEObjectImpl.Container implements IWorkbenchState {
	protected MPerspective perspective;

	protected EList<IPartMemento> viewSettings;

	protected MArea editorArea;

	protected EList<MTrimBar> trimBars;

	protected WorkbenchState() {
		super();
	}

	@Override
	protected EClass eStaticClass() {
		return IPersistencePackage.Literals.WORKBENCH_STATE;
	}

	@Override
	public MPerspective getPerspective() {
		return perspective;
	}

	public NotificationChain basicSetPerspective(MPerspective newPerspective, NotificationChain msgs) {
		MPerspective oldPerspective = perspective;
		perspective = newPerspective;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IPersistencePackage.WORKBENCH_STATE__PERSPECTIVE, oldPerspective, newPerspective);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	@Override
	public void setPerspective(MPerspective newPerspective) {
		if (newPerspective != perspective) {
			NotificationChain msgs = null;
			if (perspective != null)
				msgs = ((InternalEObject)perspective).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IPersistencePackage.WORKBENCH_STATE__PERSPECTIVE, null, msgs);
			if (newPerspective != null)
				msgs = ((InternalEObject)newPerspective).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IPersistencePackage.WORKBENCH_STATE__PERSPECTIVE, null, msgs);
			msgs = basicSetPerspective(newPerspective, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IPersistencePackage.WORKBENCH_STATE__PERSPECTIVE, newPerspective, newPerspective));
	}

	@Override
	public EList<IPartMemento> getViewSettings() {
		if (viewSettings == null) {
			viewSettings = new EObjectContainmentEList<IPartMemento>(IPartMemento.class, this, IPersistencePackage.WORKBENCH_STATE__VIEW_SETTINGS);
		}
		return viewSettings;
	}

	@Override
	public MArea getEditorArea() {
		return editorArea;
	}

	public NotificationChain basicSetEditorArea(MArea newEditorArea, NotificationChain msgs) {
		MArea oldEditorArea = editorArea;
		editorArea = newEditorArea;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IPersistencePackage.WORKBENCH_STATE__EDITOR_AREA, oldEditorArea, newEditorArea);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	@Override
	public void setEditorArea(MArea newEditorArea) {
		if (newEditorArea != editorArea) {
			NotificationChain msgs = null;
			if (editorArea != null)
				msgs = ((InternalEObject)editorArea).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IPersistencePackage.WORKBENCH_STATE__EDITOR_AREA, null, msgs);
			if (newEditorArea != null)
				msgs = ((InternalEObject)newEditorArea).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IPersistencePackage.WORKBENCH_STATE__EDITOR_AREA, null, msgs);
			msgs = basicSetEditorArea(newEditorArea, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IPersistencePackage.WORKBENCH_STATE__EDITOR_AREA, newEditorArea, newEditorArea));
	}

	@Override
	public EList<MTrimBar> getTrimBars() {
		if (trimBars == null) {
			trimBars = new EObjectContainmentEList<MTrimBar>(MTrimBar.class, this, IPersistencePackage.WORKBENCH_STATE__TRIM_BARS);
		}
		return trimBars;
	}

	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IPersistencePackage.WORKBENCH_STATE__PERSPECTIVE:
				return basicSetPerspective(null, msgs);
			case IPersistencePackage.WORKBENCH_STATE__VIEW_SETTINGS:
				return ((InternalEList<?>)getViewSettings()).basicRemove(otherEnd, msgs);
			case IPersistencePackage.WORKBENCH_STATE__EDITOR_AREA:
				return basicSetEditorArea(null, msgs);
			case IPersistencePackage.WORKBENCH_STATE__TRIM_BARS:
				return ((InternalEList<?>)getTrimBars()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case IPersistencePackage.WORKBENCH_STATE__PERSPECTIVE:
				return getPerspective();
			case IPersistencePackage.WORKBENCH_STATE__VIEW_SETTINGS:
				return getViewSettings();
			case IPersistencePackage.WORKBENCH_STATE__EDITOR_AREA:
				return getEditorArea();
			case IPersistencePackage.WORKBENCH_STATE__TRIM_BARS:
				return getTrimBars();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case IPersistencePackage.WORKBENCH_STATE__PERSPECTIVE:
				setPerspective((MPerspective)newValue);
				return;
			case IPersistencePackage.WORKBENCH_STATE__VIEW_SETTINGS:
				getViewSettings().clear();
				getViewSettings().addAll((Collection<? extends IPartMemento>)newValue);
				return;
			case IPersistencePackage.WORKBENCH_STATE__EDITOR_AREA:
				setEditorArea((MArea)newValue);
				return;
			case IPersistencePackage.WORKBENCH_STATE__TRIM_BARS:
				getTrimBars().clear();
				getTrimBars().addAll((Collection<? extends MTrimBar>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case IPersistencePackage.WORKBENCH_STATE__PERSPECTIVE:
				setPerspective((MPerspective)null);
				return;
			case IPersistencePackage.WORKBENCH_STATE__VIEW_SETTINGS:
				getViewSettings().clear();
				return;
			case IPersistencePackage.WORKBENCH_STATE__EDITOR_AREA:
				setEditorArea((MArea)null);
				return;
			case IPersistencePackage.WORKBENCH_STATE__TRIM_BARS:
				getTrimBars().clear();
				return;
		}
		super.eUnset(featureID);
	}

	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case IPersistencePackage.WORKBENCH_STATE__PERSPECTIVE:
				return perspective != null;
			case IPersistencePackage.WORKBENCH_STATE__VIEW_SETTINGS:
				return viewSettings != null && !viewSettings.isEmpty();
			case IPersistencePackage.WORKBENCH_STATE__EDITOR_AREA:
				return editorArea != null;
			case IPersistencePackage.WORKBENCH_STATE__TRIM_BARS:
				return trimBars != null && !trimBars.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //WorkbenchState
