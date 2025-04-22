
package org.eclipse.ui.internal;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.eclipse.e4.core.commands.ExpressionContext;
import org.eclipse.e4.ui.internal.workbench.ContributionsAnalyzer;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.commands.MCommand;
import org.eclipse.e4.ui.model.application.ui.menu.MHandledMenuItem;
import org.eclipse.e4.ui.model.application.ui.menu.MMenu;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuContribution;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuElement;
import org.eclipse.e4.ui.model.application.ui.menu.impl.MenuFactoryImpl;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISourceProvider;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.commands.ICommandImageService;
import org.eclipse.ui.internal.services.IWorkbenchLocationService;
import org.eclipse.ui.internal.services.WorkbenchSourceProvider;
import org.eclipse.ui.internal.util.Util;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;
import org.eclipse.ui.menus.IWorkbenchContribution;
import org.eclipse.ui.menus.MenuUtil;
import org.eclipse.ui.part.IShowInSource;
import org.eclipse.ui.part.IShowInTargetList;
import org.eclipse.ui.part.ShowInContext;
import org.eclipse.ui.services.IServiceLocator;
import org.eclipse.ui.services.ISourceProviderService;
import org.eclipse.ui.views.IViewDescriptor;
import org.eclipse.ui.views.IViewRegistry;

public class ShowInMenu extends ContributionItem implements
		IWorkbenchContribution {

	private static final String NO_TARGETS_MSG = WorkbenchMessages.Workbench_showInNoTargets;

	private IWorkbenchWindow window;

	private boolean dirty = true;

	private IMenuListener menuListener = new IMenuListener() {
		@Override
		public void menuAboutToShow(IMenuManager manager) {
			manager.markDirty();
			dirty = true;
		}
	};

	private IServiceLocator locator;

	private MenuManager currentManager;

	public ShowInMenu() {

	}

	public ShowInMenu(IWorkbenchWindow window, String id) {
		super(id);
		this.window = window;
	}

	@Override
	public boolean isDirty() {
		return dirty;
	}

	@Override
	public boolean isDynamic() {
		return true;
	}

	@Override
	public void fill(Menu menu, int index) {
		if (getParent() instanceof MenuManager) {
			((MenuManager) getParent()).addMenuListener(menuListener);
		}

		if (!dirty) {
			return;
		}

		if (currentManager!=null && currentManager.getSize() > 0) {
			currentManager.removeAll();
		}

		currentManager = new MenuManager();
		fillMenu(currentManager);
		int itemCount = menu.getItemCount();
		IContributionItem[] items = currentManager.getItems();
		if (items.length == 0) {
			MenuItem item = new MenuItem(menu, SWT.NONE, index == -1 ? itemCount : index);
			item.setText(NO_TARGETS_MSG);
			item.setEnabled(false);
		} else {
			for (int i = 0; i < items.length; i++) {
				IContributionItem item = items[i];
				if (item.isVisible()) {
					if (index == -1) {
						item.fill(menu, -1);
					} else {
						item.fill(menu, index);
						int newItemCount = menu.getItemCount();
						index += newItemCount - itemCount;
						itemCount = newItemCount;
					}
				}
			}
		}
		dirty = false;
	}

	private void fillMenu(IMenuManager innerMgr) {
		IWorkbenchPage page = locator.getService(IWorkbenchPage.class);
		if (page == null) {
			return;
		}
		WorkbenchPartReference r = (WorkbenchPartReference) page.getActivePartReference();
		if (page != null && r != null && r.getModel() != null) {
			((WorkbenchPage) page).updateShowInSources(r.getModel());
		}

		innerMgr.removeAll();

		IWorkbenchPart sourcePart = getSourcePart();
		ShowInContext context = getContext(sourcePart);
		if (context == null) {
			return;
		}
		if (context.getInput() == null
				&& (context.getSelection() == null || context.getSelection()
						.isEmpty())) {
			return;
		}

		IViewDescriptor[] viewDescs = getViewDescriptors(sourcePart);
		for (int i = 0; i < viewDescs.length; ++i) {
			IContributionItem cci = getContributionItem(viewDescs[i]);
			if (cci != null) {
				innerMgr.add(cci);
			}
		}
		if (sourcePart != null && innerMgr instanceof MenuManager) {
			ISourceProviderService sps = locator
					.getService(ISourceProviderService.class);
			ISourceProvider sp = sps
					.getSourceProvider(ISources.SHOW_IN_SELECTION);
			if (sp instanceof WorkbenchSourceProvider) {
				((WorkbenchSourceProvider) sp).checkActivePart(true);
			}

			String location = MenuUtil.SHOW_IN_MENU_ID;
			location = location.substring(location.indexOf(':') + 1);
			WorkbenchWindow workbenchWindow = (WorkbenchWindow) getWindow();
			MApplication application = workbenchWindow.getModel().getContext()
					.get(MApplication.class);

			MMenu menuModel = MenuFactoryImpl.eINSTANCE.createMenu();
			final ArrayList<MMenuContribution> toContribute = new ArrayList<MMenuContribution>();
			final ArrayList<MMenuElement> menuContributionsToRemove = new ArrayList<MMenuElement>();
			ExpressionContext eContext = new ExpressionContext(workbenchWindow.getModel()
					.getContext());
			ContributionsAnalyzer.gatherMenuContributions(menuModel,
					application.getMenuContributions(), location, toContribute, eContext, true);
			ContributionsAnalyzer.addMenuContributions(menuModel, toContribute,
					menuContributionsToRemove);

			ICommandImageService imgService = (ICommandImageService) workbenchWindow
					.getService(ICommandImageService.class);

			for (MMenuElement menuElement : menuModel.getChildren()) {
				if (menuElement instanceof MHandledMenuItem) {
					MCommand command = ((MHandledMenuItem) menuElement).getCommand();
					String commandId = command.getElementId();
					CommandContributionItemParameter ccip = new CommandContributionItemParameter(
							workbenchWindow, commandId, commandId,
							CommandContributionItem.STYLE_PUSH);
					String label = menuElement.getLabel();
					if (label != null && label.length() > 0) {
						ccip.label = label;
						String mnemonics = menuElement.getMnemonics();
						if (mnemonics != null && mnemonics.length() == 1) {
							ccip.mnemonic = mnemonics;
						} else {
							ccip.mnemonic = label.substring(0, 1);
						}
					}
					String iconURI = menuElement.getIconURI();
					try {
						ccip.icon = ImageDescriptor.createFromURL(new URL(iconURI));
					} catch (MalformedURLException e) {
						ccip.icon = imgService.getImageDescriptor(commandId);
					}
					innerMgr.add(new CommandContributionItem(ccip));
				}
			}
		}
	}

	protected IContributionItem getContributionItem(IViewDescriptor viewDescriptor) {
		CommandContributionItemParameter parm = new CommandContributionItemParameter(
				locator, viewDescriptor.getId(), IWorkbenchCommandConstants.NAVIGATE_SHOW_IN,
				CommandContributionItem.STYLE_PUSH);
		HashMap<String, String> targetId = new HashMap<String, String>();
		targetId.put(IWorkbenchCommandConstants.NAVIGATE_SHOW_IN_PARM_TARGET,
				viewDescriptor.getId());
		parm.parameters = targetId;
		parm.label = viewDescriptor.getLabel();
		if (parm.label.length() > 0) {
			parm.mnemonic = parm.label.substring(0, 1);
		}
		parm.icon = viewDescriptor.getImageDescriptor();
		return new CommandContributionItem(parm);
	}

	private ArrayList<Object> getShowInPartIds(IWorkbenchPart sourcePart) {
		ArrayList<Object> targetIds = new ArrayList<Object>();
		WorkbenchPage page = (WorkbenchPage) getWindow().getActivePage();
		if (page != null) {
			String srcId = sourcePart == null ? null : sourcePart.getSite().getId();
			ArrayList<?> pagePartIds = page.getShowInPartIds();
			for (Object pagePartId : pagePartIds) {
				if (!pagePartId.equals(srcId)) {
					targetIds.add(pagePartId);
				}
			}
		}
		IShowInTargetList targetList = getShowInTargetList(sourcePart);
		if (targetList != null) {
			String[] partIds = targetList.getShowInTargetIds();
			if (partIds != null) {
				for (int i = 0; i < partIds.length; ++i) {
					if (!targetIds.contains(partIds[i])) {
						targetIds.add(partIds[i]);
					}
				}
			}
		}
		page.sortShowInPartIds(targetIds);
		return targetIds;
	}

	protected IWorkbenchPart getSourcePart() {
		IWorkbenchWindow window = getWindow();

		if (window == null)
			return null;

		IWorkbenchPage page = window.getActivePage();
		return page != null ? page.getActivePart() : null;
	}

	private IShowInSource getShowInSource(IWorkbenchPart sourcePart) {
		return (IShowInSource) Util.getAdapter(sourcePart, IShowInSource.class);
	}

	private IShowInTargetList getShowInTargetList(IWorkbenchPart sourcePart) {
		return (IShowInTargetList) Util.getAdapter(sourcePart,
				IShowInTargetList.class);
	}

	protected ShowInContext getContext(IWorkbenchPart sourcePart) {
		if (sourcePart != null) {
			IShowInSource source = getShowInSource(sourcePart);
			if (source != null) {
				ShowInContext context = source.getShowInContext();
				if (context != null) {
					return context;
				}
			} else if (sourcePart instanceof IEditorPart) {
				Object input = ((IEditorPart) sourcePart).getEditorInput();
				ISelectionProvider sp = sourcePart.getSite().getSelectionProvider();
				ISelection sel = sp == null ? null : sp.getSelection();
				return new ShowInContext(input, sel);
			}
		}
		return null;
	}

	private IViewDescriptor[] getViewDescriptors(IWorkbenchPart sourcePart) {
		ArrayList<Object> ids = getShowInPartIds(sourcePart);
		ArrayList<IViewDescriptor> descs = new ArrayList<IViewDescriptor>();
		IViewRegistry reg = WorkbenchPlugin.getDefault().getViewRegistry();
		for (Iterator<Object> i = ids.iterator(); i.hasNext();) {
			String id = (String) i.next();
			IViewDescriptor desc = reg.find(id);
			if (desc != null) {
				descs.add(desc);
			}
		}
		return descs.toArray(new IViewDescriptor[descs
				.size()]);
	}

	@Override
	public void initialize(IServiceLocator serviceLocator) {
		locator = serviceLocator;
	}

	protected IWorkbenchWindow getWindow() {
		if (locator == null)
			return null;
		
		IWorkbenchLocationService wls = locator
				.getService(IWorkbenchLocationService.class);

		if (window == null) {
			window = wls.getWorkbenchWindow();
		}
		if (window == null) {
			IWorkbench wb = wls.getWorkbench();
			if (wb != null) {
				window = wb.getActiveWorkbenchWindow();
			}
		}
		return window;
	}

	@Override
	public void dispose() {
		if (currentManager != null && currentManager.getSize() > 0) {
			currentManager.removeAll();
			currentManager = null;
		}
		if (getParent() instanceof MenuManager) {
			((MenuManager) getParent()).removeMenuListener(menuListener);
		}
		window=null;
		locator=null;
	}
}
