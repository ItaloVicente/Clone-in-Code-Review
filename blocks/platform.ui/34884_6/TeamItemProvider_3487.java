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
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HockeyleaguePackage;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HockeyleaguePlugin;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.PlayerStats;

public class PlayerStatsItemProvider
	extends ItemProviderAdapter
	implements
		IEditingDomainItemProvider,
		IStructuredItemContentProvider,
		ITreeItemContentProvider,
		IItemLabelProvider,
		IItemPropertySource {
	public PlayerStatsItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	public List getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addYearPropertyDescriptor(object);
			addTeamPropertyDescriptor(object);
			addGamesPlayedInPropertyDescriptor(object);
			addGoalsPropertyDescriptor(object);
			addAssistsPropertyDescriptor(object);
			addPointsPropertyDescriptor(object);
			addPlusMinusPropertyDescriptor(object);
			addPenaltyMinutesPropertyDescriptor(object);
			addPowerPlayGoalsPropertyDescriptor(object);
			addShortHandedGoalsPropertyDescriptor(object);
			addGameWinningGoalsPropertyDescriptor(object);
			addShotsPropertyDescriptor(object);
			addShotPercentagePropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	protected void addYearPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_PlayerStats_year_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_PlayerStats_year_feature", "_UI_PlayerStats_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 HockeyleaguePackage.Literals.PLAYER_STATS__YEAR,
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
				 getString("_UI_PlayerStats_team_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_PlayerStats_team_feature", "_UI_PlayerStats_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 HockeyleaguePackage.Literals.PLAYER_STATS__TEAM,
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
				 getString("_UI_PlayerStats_gamesPlayedIn_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_PlayerStats_gamesPlayedIn_feature", "_UI_PlayerStats_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 HockeyleaguePackage.Literals.PLAYER_STATS__GAMES_PLAYED_IN,
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
				 getString("_UI_PlayerStats_goals_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_PlayerStats_goals_feature", "_UI_PlayerStats_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 HockeyleaguePackage.Literals.PLAYER_STATS__GOALS,
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
				 getString("_UI_PlayerStats_assists_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_PlayerStats_assists_feature", "_UI_PlayerStats_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 HockeyleaguePackage.Literals.PLAYER_STATS__ASSISTS,
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
				 getString("_UI_PlayerStats_points_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_PlayerStats_points_feature", "_UI_PlayerStats_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 HockeyleaguePackage.Literals.PLAYER_STATS__POINTS,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 null,
				 null));
	}

	protected void addPlusMinusPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_PlayerStats_plusMinus_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_PlayerStats_plusMinus_feature", "_UI_PlayerStats_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 HockeyleaguePackage.Literals.PLAYER_STATS__PLUS_MINUS,
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
				 getString("_UI_PlayerStats_penaltyMinutes_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_PlayerStats_penaltyMinutes_feature", "_UI_PlayerStats_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 HockeyleaguePackage.Literals.PLAYER_STATS__PENALTY_MINUTES,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 null,
				 null));
	}

	protected void addPowerPlayGoalsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_PlayerStats_powerPlayGoals_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_PlayerStats_powerPlayGoals_feature", "_UI_PlayerStats_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 HockeyleaguePackage.Literals.PLAYER_STATS__POWER_PLAY_GOALS,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 null,
				 null));
	}

	protected void addShortHandedGoalsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_PlayerStats_shortHandedGoals_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_PlayerStats_shortHandedGoals_feature", "_UI_PlayerStats_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 HockeyleaguePackage.Literals.PLAYER_STATS__SHORT_HANDED_GOALS,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 null,
				 null));
	}

	protected void addGameWinningGoalsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_PlayerStats_gameWinningGoals_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_PlayerStats_gameWinningGoals_feature", "_UI_PlayerStats_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 HockeyleaguePackage.Literals.PLAYER_STATS__GAME_WINNING_GOALS,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 null,
				 null));
	}

	protected void addShotsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_PlayerStats_shots_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_PlayerStats_shots_feature", "_UI_PlayerStats_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 HockeyleaguePackage.Literals.PLAYER_STATS__SHOTS,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 null,
				 null));
	}

	protected void addShotPercentagePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_PlayerStats_shotPercentage_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_PlayerStats_shotPercentage_feature", "_UI_PlayerStats_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 HockeyleaguePackage.Literals.PLAYER_STATS__SHOT_PERCENTAGE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.REAL_VALUE_IMAGE,
				 null,
				 null));
	}

	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/PlayerStats")); //$NON-NLS-1$
	}

	public String getText(Object object) {
		String label = ((PlayerStats)object).getYear();
		return label == null || label.length() == 0 ?
			getString("_UI_PlayerStats_type") : //$NON-NLS-1$
			getString("_UI_PlayerStats_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
	}

	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(PlayerStats.class)) {
			case HockeyleaguePackage.PLAYER_STATS__YEAR:
			case HockeyleaguePackage.PLAYER_STATS__GAMES_PLAYED_IN:
			case HockeyleaguePackage.PLAYER_STATS__GOALS:
			case HockeyleaguePackage.PLAYER_STATS__ASSISTS:
			case HockeyleaguePackage.PLAYER_STATS__POINTS:
			case HockeyleaguePackage.PLAYER_STATS__PLUS_MINUS:
			case HockeyleaguePackage.PLAYER_STATS__PENALTY_MINUTES:
			case HockeyleaguePackage.PLAYER_STATS__POWER_PLAY_GOALS:
			case HockeyleaguePackage.PLAYER_STATS__SHORT_HANDED_GOALS:
			case HockeyleaguePackage.PLAYER_STATS__GAME_WINNING_GOALS:
			case HockeyleaguePackage.PLAYER_STATS__SHOTS:
			case HockeyleaguePackage.PLAYER_STATS__SHOT_PERCENTAGE:
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
