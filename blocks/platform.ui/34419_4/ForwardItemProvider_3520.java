package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.Defence;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HockeyleagueFactory;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HockeyleaguePackage;

public class DefenceItemProvider
	extends PlayerItemProvider
	implements
		IEditingDomainItemProvider,
		IStructuredItemContentProvider,
		ITreeItemContentProvider,
		IItemLabelProvider,
		IItemPropertySource {
	public DefenceItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	public List getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addPositionPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	protected void addPositionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Defence_position_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_Defence_position_feature", "_UI_Defence_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 HockeyleaguePackage.Literals.DEFENCE__POSITION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	public Collection getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(HockeyleaguePackage.Literals.DEFENCE__PLAYER_STATS);
		}
		return childrenFeatures;
	}

	protected EStructuralFeature getChildFeature(Object object, Object child) {

		return super.getChildFeature(object, child);
	}

	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/Defence")); //$NON-NLS-1$
	}

	public String getText(Object object) {
		String label = ((Defence)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_Defence_type") : //$NON-NLS-1$
			getString("_UI_Defence_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
	}

	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(Defence.class)) {
			case HockeyleaguePackage.DEFENCE__POSITION:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case HockeyleaguePackage.DEFENCE__PLAYER_STATS:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
				return;
		}
		super.notifyChanged(notification);
	}

	protected void collectNewChildDescriptors(Collection newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add
			(createChildParameter
				(HockeyleaguePackage.Literals.DEFENCE__PLAYER_STATS,
				 HockeyleagueFactory.eINSTANCE.createPlayerStats()));
	}

}
