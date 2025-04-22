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

import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HockeyleagueFactory;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HockeyleaguePackage;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.Team;

public class TeamItemProvider
	extends HockeyleagueObjectItemProvider
	implements
		IEditingDomainItemProvider,
		IStructuredItemContentProvider,
		ITreeItemContentProvider,
		IItemLabelProvider,
		IItemPropertySource {
	public TeamItemProvider(AdapterFactory adapterFactory) {
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
			childrenFeatures.add(HockeyleaguePackage.Literals.TEAM__FORWARDS);
			childrenFeatures.add(HockeyleaguePackage.Literals.TEAM__DEFENCEMEN);
			childrenFeatures.add(HockeyleaguePackage.Literals.TEAM__GOALIES);
			childrenFeatures.add(HockeyleaguePackage.Literals.TEAM__ARENA);
		}
		return childrenFeatures;
	}

	protected EStructuralFeature getChildFeature(Object object, Object child) {

		return super.getChildFeature(object, child);
	}

	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/Team")); //$NON-NLS-1$
	}

	public String getText(Object object) {
		String label = ((Team)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_Team_type") : //$NON-NLS-1$
			getString("_UI_Team_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
	}

	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(Team.class)) {
			case HockeyleaguePackage.TEAM__FORWARDS:
			case HockeyleaguePackage.TEAM__DEFENCEMEN:
			case HockeyleaguePackage.TEAM__GOALIES:
			case HockeyleaguePackage.TEAM__ARENA:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
				return;
		}
		super.notifyChanged(notification);
	}

	protected void collectNewChildDescriptors(Collection newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add
			(createChildParameter
				(HockeyleaguePackage.Literals.TEAM__FORWARDS,
				 HockeyleagueFactory.eINSTANCE.createForward()));

		newChildDescriptors.add
			(createChildParameter
				(HockeyleaguePackage.Literals.TEAM__DEFENCEMEN,
				 HockeyleagueFactory.eINSTANCE.createDefence()));

		newChildDescriptors.add
			(createChildParameter
				(HockeyleaguePackage.Literals.TEAM__GOALIES,
				 HockeyleagueFactory.eINSTANCE.createGoalie()));

		newChildDescriptors.add
			(createChildParameter
				(HockeyleaguePackage.Literals.TEAM__ARENA,
				 HockeyleagueFactory.eINSTANCE.createArena()));
	}

}
