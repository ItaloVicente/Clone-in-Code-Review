package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.provider;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.edit.provider.ChangeNotifier;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;

import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.util.HockeyleagueAdapterFactory;

public class HockeyleagueItemProviderAdapterFactory extends HockeyleagueAdapterFactory implements ComposeableAdapterFactory, IChangeNotifier, IDisposable {
	protected ComposedAdapterFactory parentAdapterFactory;

	protected IChangeNotifier changeNotifier = new ChangeNotifier();

	protected Collection supportedTypes = new ArrayList();

	public HockeyleagueItemProviderAdapterFactory() {
		supportedTypes.add(IEditingDomainItemProvider.class);
		supportedTypes.add(IStructuredItemContentProvider.class);
		supportedTypes.add(ITreeItemContentProvider.class);
		supportedTypes.add(IItemLabelProvider.class);
		supportedTypes.add(IItemPropertySource.class);
	}

	protected ArenaItemProvider arenaItemProvider;

	public Adapter createArenaAdapter() {
		if (arenaItemProvider == null) {
			arenaItemProvider = new ArenaItemProvider(this);
		}

		return arenaItemProvider;
	}

	protected DefenceItemProvider defenceItemProvider;

	public Adapter createDefenceAdapter() {
		if (defenceItemProvider == null) {
			defenceItemProvider = new DefenceItemProvider(this);
		}

		return defenceItemProvider;
	}

	protected ForwardItemProvider forwardItemProvider;

	public Adapter createForwardAdapter() {
		if (forwardItemProvider == null) {
			forwardItemProvider = new ForwardItemProvider(this);
		}

		return forwardItemProvider;
	}

	protected GoalieItemProvider goalieItemProvider;

	public Adapter createGoalieAdapter() {
		if (goalieItemProvider == null) {
			goalieItemProvider = new GoalieItemProvider(this);
		}

		return goalieItemProvider;
	}

	protected GoalieStatsItemProvider goalieStatsItemProvider;

	public Adapter createGoalieStatsAdapter() {
		if (goalieStatsItemProvider == null) {
			goalieStatsItemProvider = new GoalieStatsItemProvider(this);
		}

		return goalieStatsItemProvider;
	}

	protected LeagueItemProvider leagueItemProvider;

	public Adapter createLeagueAdapter() {
		if (leagueItemProvider == null) {
			leagueItemProvider = new LeagueItemProvider(this);
		}

		return leagueItemProvider;
	}

	protected PlayerStatsItemProvider playerStatsItemProvider;

	public Adapter createPlayerStatsAdapter() {
		if (playerStatsItemProvider == null) {
			playerStatsItemProvider = new PlayerStatsItemProvider(this);
		}

		return playerStatsItemProvider;
	}

	protected TeamItemProvider teamItemProvider;

	public Adapter createTeamAdapter() {
		if (teamItemProvider == null) {
			teamItemProvider = new TeamItemProvider(this);
		}

		return teamItemProvider;
	}

	public ComposeableAdapterFactory getRootAdapterFactory() {
		return parentAdapterFactory == null ? this : parentAdapterFactory.getRootAdapterFactory();
	}

	public void setParentAdapterFactory(ComposedAdapterFactory parentAdapterFactory) {
		this.parentAdapterFactory = parentAdapterFactory;
	}

	public boolean isFactoryForType(Object type) {
		return supportedTypes.contains(type) || super.isFactoryForType(type);
	}

	public Adapter adapt(Notifier notifier, Object type) {
		return super.adapt(notifier, this);
	}

	public Object adapt(Object object, Object type) {
		if (isFactoryForType(type)) {
			Object adapter = super.adapt(object, type);
			if (!(type instanceof Class) || (((Class)type).isInstance(adapter))) {
				return adapter;
			}
		}

		return null;
	}

	public void addListener(INotifyChangedListener notifyChangedListener) {
		changeNotifier.addListener(notifyChangedListener);
	}

	public void removeListener(INotifyChangedListener notifyChangedListener) {
		changeNotifier.removeListener(notifyChangedListener);
	}

	public void fireNotifyChanged(Notification notification) {
		changeNotifier.fireNotifyChanged(notification);

		if (parentAdapterFactory != null) {
			parentAdapterFactory.fireNotifyChanged(notification);
		}
	}

	public void dispose() {
		if (arenaItemProvider != null) arenaItemProvider.dispose();
		if (defenceItemProvider != null) defenceItemProvider.dispose();
		if (forwardItemProvider != null) forwardItemProvider.dispose();
		if (goalieItemProvider != null) goalieItemProvider.dispose();
		if (goalieStatsItemProvider != null) goalieStatsItemProvider.dispose();
		if (leagueItemProvider != null) leagueItemProvider.dispose();
		if (playerStatsItemProvider != null) playerStatsItemProvider.dispose();
		if (teamItemProvider != null) teamItemProvider.dispose();
	}

}
