package org.eclipse.ui.navigator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.activities.WorkbenchActivityHelper;
import org.eclipse.ui.internal.navigator.NavigatorContentService;
import org.eclipse.ui.internal.navigator.NavigatorSafeRunnable;
import org.eclipse.ui.internal.navigator.actions.CommonActionDescriptorManager;
import org.eclipse.ui.internal.navigator.actions.CommonActionProviderDescriptor;
import org.eclipse.ui.internal.navigator.extensions.CommonActionExtensionSite;
import org.eclipse.ui.internal.navigator.extensions.SkeletonActionProvider;

public final class NavigatorActionService extends ActionGroup implements IMementoAware {

	private static final IContributionItem[] DEFAULT_GROUPS = new IContributionItem[]{new Separator(ICommonMenuConstants.GROUP_NEW), new GroupMarker(ICommonMenuConstants.GROUP_GOTO), new GroupMarker(ICommonMenuConstants.GROUP_OPEN), new GroupMarker(ICommonMenuConstants.GROUP_OPEN_WITH), new Separator(ICommonMenuConstants.GROUP_EDIT), new GroupMarker(ICommonMenuConstants.GROUP_SHOW), new GroupMarker(ICommonMenuConstants.GROUP_REORGANIZE), new GroupMarker(ICommonMenuConstants.GROUP_PORT), new Separator(ICommonMenuConstants.GROUP_GENERATE), new Separator(ICommonMenuConstants.GROUP_SEARCH), new Separator(ICommonMenuConstants.GROUP_BUILD), new Separator(ICommonMenuConstants.GROUP_ADDITIONS), new Separator(ICommonMenuConstants.GROUP_PROPERTIES)};

	private final ICommonViewerSite commonViewerSite;

	private final StructuredViewer structuredViewer;

	private final NavigatorContentService contentService;

	private final INavigatorViewerDescriptor viewerDescriptor;

	private final Set actionProviderDescriptors = new HashSet();

	private final Map actionProviderInstances = new HashMap();

	private IMemento memento;

	private IContributionItem[] menuGroups;

	private boolean disposed = false;

	public NavigatorActionService(ICommonViewerSite aCommonViewerSite, StructuredViewer aStructuredViewer, INavigatorContentService aContentService) {
		super();
		Assert.isNotNull(aCommonViewerSite);
		Assert.isNotNull(aStructuredViewer);
		Assert.isNotNull(aContentService);

		commonViewerSite = aCommonViewerSite;
		contentService = (NavigatorContentService) aContentService;
		structuredViewer = aStructuredViewer;
		viewerDescriptor = contentService.getViewerDescriptor();

	}

	public void prepareMenuForPlatformContributions(MenuManager menu, ISelectionProvider aSelectionProvider, boolean force) {
		Assert.isTrue(!disposed);

		if (commonViewerSite instanceof ICommonViewerWorkbenchSite) {
			if (force || viewerDescriptor.allowsPlatformContributionsToContextMenu()) {
				((ICommonViewerWorkbenchSite) commonViewerSite).registerContextMenu(contentService.getViewerDescriptor().getPopupMenuId(), menu, aSelectionProvider);
			}
		}
	}

	@Override
	public void fillContextMenu(IMenuManager aMenu) {
		Assert.isTrue(!disposed);

		if (menuGroups == null) {
			createMenuGroups();
		}

		for (int i = 0; i < menuGroups.length; i++) {
			aMenu.add(menuGroups[i]);
		}

		addCommonActionProviderMenu(aMenu);
	}

	private void createMenuGroups() {
		MenuInsertionPoint[] customPoints = viewerDescriptor.getCustomInsertionPoints();

		if (customPoints == null) {
			menuGroups = DEFAULT_GROUPS;
		} else {
			menuGroups = new IContributionItem[customPoints.length];
			for (int i = 0; i < customPoints.length; i++) {
				if (customPoints[i].isSeparator()) {
					menuGroups[i] = new Separator(customPoints[i].getName());
				} else {
					menuGroups[i] = new GroupMarker(customPoints[i].getName());
				}
			}
		}
	}

	private boolean filterActionProvider(final CommonActionProviderDescriptor providerDesc) {
		IPluginContribution piCont = new IPluginContribution() {
			@Override
			public String getLocalId() {
				return providerDesc.getId();
			}

			@Override
			public String getPluginId() {
				return providerDesc.getPluginId();
			}
		};

		return WorkbenchActivityHelper.filterItem(piCont);
	}
	
	private void addCommonActionProviderMenu(final IMenuManager aMenu) {
		final CommonActionProviderDescriptor[] providerDescriptors = CommonActionDescriptorManager
				.getInstance().findRelevantActionDescriptors(contentService, getContext());
		if (providerDescriptors.length > 0) {
			for (int i = 0; i < providerDescriptors.length; i++) {
				final CommonActionProviderDescriptor providerDescriptorLocal = providerDescriptors[i];
				SafeRunner.run(new NavigatorSafeRunnable() {
					@Override
					public void run() throws Exception {
						if (!filterActionProvider(providerDescriptorLocal)) {
							CommonActionProvider provider = getActionProviderInstance(providerDescriptorLocal);
							provider.setContext(getContext());
							provider.fillContextMenu(aMenu);
						}
					}
				});
			}
		}
	}
	
	@Override
	public void fillActionBars(final IActionBars theActionBars) {
		Assert.isTrue(!disposed);

		theActionBars.clearGlobalActionHandlers();
		ActionContext context = getContext();
		if (context == null) {
			context = new ActionContext(StructuredSelection.EMPTY);
		}

		final CommonActionProviderDescriptor[] providerDescriptors = CommonActionDescriptorManager
				.getInstance().findRelevantActionDescriptors(contentService, context);
		if (providerDescriptors.length > 0) {
			for (int i = 0; i < providerDescriptors.length; i++) {
				final CommonActionProviderDescriptor providerDesciptorLocal = providerDescriptors[i];
				final ActionContext actionContextLocal = context;
				SafeRunner.run(new NavigatorSafeRunnable() {
					@Override
					public void run() throws Exception {
						if (!filterActionProvider(providerDesciptorLocal)) {
							CommonActionProvider provider = null;
							provider = getActionProviderInstance(providerDesciptorLocal);
							provider.setContext(actionContextLocal);
							provider.fillActionBars(theActionBars);
							provider.updateActionBars();
						}
					}
				});
			}
		}
		theActionBars.updateActionBars();
		theActionBars.getMenuManager().update();
	}

	@Override
	public void dispose() {
		synchronized (actionProviderInstances) {
			for (Iterator iter = actionProviderInstances.values().iterator(); iter.hasNext();) {
				CommonActionProvider element = (CommonActionProvider) iter.next();
				element.dispose();
			}
			actionProviderInstances.clear();
		}
		actionProviderDescriptors.clear();
		disposed = false;
	}

	@Override
	public void restoreState(IMemento aMemento) {
		Assert.isTrue(!disposed);
		memento = aMemento;

		synchronized (actionProviderInstances) {
			for (Iterator actionProviderIterator = actionProviderInstances.values().iterator(); actionProviderIterator
					.hasNext();) {
				final CommonActionProvider provider = (CommonActionProvider) actionProviderIterator
						.next();
				SafeRunner.run(new NavigatorSafeRunnable() {
					@Override
					public void run() throws Exception {
						provider.restoreState(memento);
					}
				});
			}
		}
	}

	@Override
	public void saveState(IMemento aMemento) {
		Assert.isTrue(!disposed);

		memento = aMemento;
		CommonActionProvider provider = null;
		synchronized (actionProviderInstances) {
			for (Iterator actionProviderIterator = actionProviderInstances.values().iterator(); actionProviderIterator.hasNext();) {
				provider = (CommonActionProvider) actionProviderIterator.next();
				provider.saveState(memento);
			}
		}
	}

	public CommonActionProvider getActionProviderInstance(
			final CommonActionProviderDescriptor aProviderDescriptor) {
		CommonActionProvider provider = null;
		provider = (CommonActionProvider) actionProviderInstances.get(aProviderDescriptor);
		if (provider != null) {
			return provider;
		}
		synchronized (actionProviderInstances) {
			provider = (CommonActionProvider) actionProviderInstances.get(aProviderDescriptor);
			if (provider == null) {
				final CommonActionProvider[] retProvider = new CommonActionProvider[1];
				SafeRunner.run(new NavigatorSafeRunnable() {
					@Override
					public void run() throws Exception {
						retProvider[0] = aProviderDescriptor.createActionProvider();
						if (retProvider[0] != null) {
							initialize(aProviderDescriptor.getId(), aProviderDescriptor
									.getPluginId(), retProvider[0]);
						}
					}
				});
				if (retProvider[0] == null)
					retProvider[0] = SkeletonActionProvider.INSTANCE;
				actionProviderInstances.put(aProviderDescriptor, retProvider[0]);
				provider = retProvider[0];
			}
		}
		return provider;
	}

	private void initialize(final String id, final String pluginId,
			final CommonActionProvider anActionProvider) {
		if (anActionProvider != null && anActionProvider != SkeletonActionProvider.INSTANCE) {
			SafeRunner.run(new NavigatorSafeRunnable() {
				@Override
				public void run() throws Exception {
					ICommonActionExtensionSite configuration = new CommonActionExtensionSite(id,
							pluginId, commonViewerSite, contentService, structuredViewer);
					anActionProvider.init(configuration);
					anActionProvider.restoreState(memento);
					anActionProvider.setContext(new ActionContext(StructuredSelection.EMPTY));
				}
			});
		}
	}
}
