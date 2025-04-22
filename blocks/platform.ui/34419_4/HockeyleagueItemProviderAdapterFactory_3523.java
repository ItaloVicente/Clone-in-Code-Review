package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.GoalieStats;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HockeyleaguePackage;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HockeyleaguePlugin;

public class GoalieStatsItemProvider
	extends ItemProviderAdapter
	implements
		IEditingDomainItemProvider,
		IStructuredItemContentProvider,
		ITreeItemContentProvider,
		IItemLabelProvider,
		IItemPropertySource {
	public GoalieStatsItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	public List getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addYearPropertyDescriptor(object);
			addTeamPropertyDescriptor(object);
			addGamesPlayedInPropertyDescriptor(object);
			addMinutesPlayedInPropertyDescriptor(object);
			addGoalsAgainstAveragePropertyDescriptor(object);
			addWinsPropertyDescriptor(object);
			addLossesPropertyDescriptor(object);
			addTiesPropertyDescriptor(object);
			addEmptyNetGoalsPropertyDescriptor(object);
			addShutoutsPropertyDescriptor(object);
			addGoalsAgainstPropertyDescriptor(object);
			addSavesPropertyDescriptor(object);
			addPenaltyMinutesPropertyDescriptor(object);
			addGoalsPropertyDescriptor(object);
			addAssistsPropertyDescriptor(object);
			addPointsPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	protected void addYearPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_GoalieStats_year_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_GoalieStats_year_feature", "_UI_GoalieStats_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 HockeyleaguePackage.Literals.GOALIE_STATS__YEAR,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	protected void addTeamPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_GoalieStats_team_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_GoalieStats_team_feature", "_UI_GoalieStats_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 HockeyleaguePackage.Literals.GOALIE_STATS__TEAM,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	protected void addGamesPlayedInPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_GoalieStats_gamesPlayedIn_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_GoalieStats_gamesPlayedIn_feature", "_UI_GoalieStats_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 HockeyleaguePackage.Literals.GOALIE_STATS__GAMES_PLAYED_IN,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 null,
				 null));
	}

	protected void addMinutesPlayedInPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_GoalieStats_minutesPlayedIn_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_GoalieStats_minutesPlayedIn_feature", "_UI_GoalieStats_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 HockeyleaguePackage.Literals.GOALIE_STATS__MINUTES_PLAYED_IN,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 null,
				 null));
	}

	protected void addGoalsAgainstAveragePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_GoalieStats_goalsAgainstAverage_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_GoalieStats_goalsAgainstAverage_feature", "_UI_GoalieStats_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 HockeyleaguePackage.Literals.GOALIE_STATS__GOALS_AGAINST_AVERAGE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.REAL_VALUE_IMAGE,
				 null,
				 null));
	}

	protected void addWinsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_GoalieStats_wins_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_GoalieStats_wins_feature", "_UI_GoalieStats_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 HockeyleaguePackage.Literals.GOALIE_STATS__WINS,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 null,
				 null));
	}

	protected void addLossesPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_GoalieStats_losses_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_GoalieStats_losses_feature", "_UI_GoalieStats_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 HockeyleaguePackage.Literals.GOALIE_STATS__LOSSES,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 null,
				 null));
	}

	protected void addTiesPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_GoalieStats_ties_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_GoalieStats_ties_feature", "_UI_GoalieStats_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 HockeyleaguePackage.Literals.GOALIE_STATS__TIES,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 null,
				 null));
	}

	protected void addEmptyNetGoalsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_GoalieStats_emptyNetGoals_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_GoalieStats_emptyNetGoals_feature", "_UI_GoalieStats_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 HockeyleaguePackage.Literals.GOALIE_STATS__EMPTY_NET_GOALS,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 null,
				 null));
	}

	protected void addShutoutsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_GoalieStats_shutouts_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_GoalieStats_shutouts_feature", "_UI_GoalieStats_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 HockeyleaguePackage.Literals.GOALIE_STATS__SHUTOUTS,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 null,
				 null));
	}

	protected void addGoalsAgainstPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_GoalieStats_goalsAgainst_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_GoalieStats_goalsAgainst_feature", "_UI_GoalieStats_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 HockeyleaguePackage.Literals.GOALIE_STATS__GOALS_AGAINST,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 null,
				 null));
	}

	protected void addSavesPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_GoalieStats_saves_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_GoalieStats_saves_feature", "_UI_GoalieStats_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 HockeyleaguePackage.Literals.GOALIE_STATS__SAVES,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 null,
				 null));
	}

	protected void addPenaltyMinutesPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_GoalieStats_penaltyMinutes_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_GoalieStats_penaltyMinutes_feature", "_UI_GoalieStats_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 HockeyleaguePackage.Literals.GOALIE_STATS__PENALTY_MINUTES,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 null,
				 null));
	}

	protected void addGoalsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_GoalieStats_goals_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_GoalieStats_goals_feature", "_UI_GoalieStats_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 HockeyleaguePackage.Literals.GOALIE_STATS__GOALS,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 null,
				 null));
	}

	protected void addAssistsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_GoalieStats_assists_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_GoalieStats_assists_feature", "_UI_GoalieStats_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 HockeyleaguePackage.Literals.GOALIE_STATS__ASSISTS,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 null,
				 null));
	}

	protected void addPointsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_GoalieStats_points_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_GoalieStats_points_feature", "_UI_GoalieStats_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 HockeyleaguePackage.Literals.GOALIE_STATS__POINTS,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 null,
				 null));
	}

	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/GoalieStats")); //$NON-NLS-1$
	}

	public String getText(Object object) {
		String label = ((GoalieStats)object).getYear();
		return label == null || label.length() == 0 ?
			getString("_UI_GoalieStats_type") : //$NON-NLS-1$
			getString("_UI_GoalieStats_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
	}

	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(GoalieStats.class)) {
			case HockeyleaguePackage.GOALIE_STATS__YEAR:
			case HockeyleaguePackage.GOALIE_STATS__GAMES_PLAYED_IN:
			case HockeyleaguePackage.GOALIE_STATS__MINUTES_PLAYED_IN:
			case HockeyleaguePackage.GOALIE_STATS__GOALS_AGAINST_AVERAGE:
			case HockeyleaguePackage.GOALIE_STATS__WINS:
			case HockeyleaguePackage.GOALIE_STATS__LOSSES:
			case HockeyleaguePackage.GOALIE_STATS__TIES:
			case HockeyleaguePackage.GOALIE_STATS__EMPTY_NET_GOALS:
			case HockeyleaguePackage.GOALIE_STATS__SHUTOUTS:
			case HockeyleaguePackage.GOALIE_STATS__GOALS_AGAINST:
			case HockeyleaguePackage.GOALIE_STATS__SAVES:
			case HockeyleaguePackage.GOALIE_STATS__PENALTY_MINUTES:
			case HockeyleaguePackage.GOALIE_STATS__GOALS:
			case HockeyleaguePackage.GOALIE_STATS__ASSISTS:
			case HockeyleaguePackage.GOALIE_STATS__POINTS:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
		}
		super.notifyChanged(notification);
	}

	protected void collectNewChildDescriptors(Collection newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);
	}

	public ResourceLocator getResourceLocator() {
		return HockeyleaguePlugin.INSTANCE;
	}

}
