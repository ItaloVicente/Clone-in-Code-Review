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

import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HockeyleaguePackage;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.Player;

public class PlayerItemProvider
	extends HockeyleagueObjectItemProvider
	implements
		IEditingDomainItemProvider,
		IStructuredItemContentProvider,
		ITreeItemContentProvider,
		IItemLabelProvider,
		IItemPropertySource {
	public PlayerItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	public List getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addBirthplacePropertyDescriptor(object);
			addNumberPropertyDescriptor(object);
			addHeightMesurementPropertyDescriptor(object);
			addHeightValuePropertyDescriptor(object);
			addWeightMesurementPropertyDescriptor(object);
			addWeightValuePropertyDescriptor(object);
			addShotPropertyDescriptor(object);
			addBirthdatePropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	protected void addBirthplacePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Player_birthplace_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_Player_birthplace_feature", "_UI_Player_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 HockeyleaguePackage.Literals.PLAYER__BIRTHPLACE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	protected void addNumberPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Player_number_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_Player_number_feature", "_UI_Player_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 HockeyleaguePackage.Literals.PLAYER__NUMBER,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 null,
				 null));
	}

	protected void addHeightMesurementPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Player_heightMesurement_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_Player_heightMesurement_feature", "_UI_Player_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 HockeyleaguePackage.Literals.PLAYER__HEIGHT_MESUREMENT,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	protected void addHeightValuePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Player_heightValue_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_Player_heightValue_feature", "_UI_Player_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 HockeyleaguePackage.Literals.PLAYER__HEIGHT_VALUE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 null,
				 null));
	}

	protected void addWeightMesurementPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Player_weightMesurement_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_Player_weightMesurement_feature", "_UI_Player_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 HockeyleaguePackage.Literals.PLAYER__WEIGHT_MESUREMENT,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	protected void addWeightValuePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Player_weightValue_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_Player_weightValue_feature", "_UI_Player_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 HockeyleaguePackage.Literals.PLAYER__WEIGHT_VALUE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 null,
				 null));
	}

	protected void addShotPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Player_shot_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_Player_shot_feature", "_UI_Player_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 HockeyleaguePackage.Literals.PLAYER__SHOT,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	protected void addBirthdatePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Player_birthdate_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_Player_birthdate_feature", "_UI_Player_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 HockeyleaguePackage.Literals.PLAYER__BIRTHDATE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	public String getText(Object object) {
		String label = ((Player)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_Player_type") : //$NON-NLS-1$
			getString("_UI_Player_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
	}

	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(Player.class)) {
			case HockeyleaguePackage.PLAYER__BIRTHPLACE:
			case HockeyleaguePackage.PLAYER__NUMBER:
			case HockeyleaguePackage.PLAYER__HEIGHT_MESUREMENT:
			case HockeyleaguePackage.PLAYER__HEIGHT_VALUE:
			case HockeyleaguePackage.PLAYER__WEIGHT_MESUREMENT:
			case HockeyleaguePackage.PLAYER__WEIGHT_VALUE:
			case HockeyleaguePackage.PLAYER__SHOT:
			case HockeyleaguePackage.PLAYER__BIRTHDATE:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
		}
		super.notifyChanged(notification);
	}

	protected void collectNewChildDescriptors(Collection newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);
	}

}
