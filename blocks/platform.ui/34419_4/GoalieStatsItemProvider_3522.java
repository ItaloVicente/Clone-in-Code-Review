package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ViewerNotification;

import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.Goalie;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HockeyleagueFactory;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HockeyleaguePackage;

public class GoalieItemProvider
	extends PlayerItemProvider
	implements
		IEditingDomainItemProvider,
		IStructuredItemContentProvider,
		ITreeItemContentProvider,
		IItemLabelProvider,
		IItemPropertySource {
	public GoalieItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	public List getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

		}
		return itemPropertyDescriptors;
	}

	public Collection getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(HockeyleaguePackage.Literals.GOALIE__GOALIE_STATS);
		}
		return childrenFeatures;
	}

	protected EStructuralFeature getChildFeature(Object object, Object child) {

		return super.getChildFeature(object, child);
	}

	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/Goalie")); //$NON-NLS-1$
	}

	public String getText(Object object) {
		String label = ((Goalie)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_Goalie_type") : //$NON-NLS-1$
			getString("_UI_Goalie_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
	}

	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(Goalie.class)) {
			case HockeyleaguePackage.GOALIE__GOALIE_STATS:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
				return;
		}
		super.notifyChanged(notification);
	}

	protected void collectNewChildDescriptors(Collection newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add
			(createChildParameter
				(HockeyleaguePackage.Literals.GOALIE__GOALIE_STATS,
				 HockeyleagueFactory.eINSTANCE.createGoalieStats()));
	}

}
