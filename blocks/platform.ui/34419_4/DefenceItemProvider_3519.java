package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.Arena;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HockeyleaguePackage;

public class ArenaItemProvider
	extends HockeyleagueObjectItemProvider
	implements
		IEditingDomainItemProvider,
		IStructuredItemContentProvider,
		ITreeItemContentProvider,
		IItemLabelProvider,
		IItemPropertySource {
	public ArenaItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	public List getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addAddressPropertyDescriptor(object);
			addCapacityPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	protected void addAddressPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Arena_address_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_Arena_address_feature", "_UI_Arena_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 HockeyleaguePackage.Literals.ARENA__ADDRESS,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	protected void addCapacityPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Arena_capacity_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_Arena_capacity_feature", "_UI_Arena_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 HockeyleaguePackage.Literals.ARENA__CAPACITY,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 null,
				 null));
	}

	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/Arena")); //$NON-NLS-1$
	}

	public String getText(Object object) {
		String label = ((Arena)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_Arena_type") : //$NON-NLS-1$
			getString("_UI_Arena_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
	}

	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(Arena.class)) {
			case HockeyleaguePackage.ARENA__ADDRESS:
			case HockeyleaguePackage.ARENA__CAPACITY:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
		}
		super.notifyChanged(notification);
	}

	protected void collectNewChildDescriptors(Collection newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);
	}

}
