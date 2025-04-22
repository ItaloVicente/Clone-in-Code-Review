package org.eclipse.ui.menus;

import java.util.Map;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.CommandEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.ICommandListener;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.IMenuListener2;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.bindings.BindingManagerEvent;
import org.eclipse.jface.bindings.IBindingManagerListener;
import org.eclipse.jface.bindings.TriggerSequence;
import org.eclipse.jface.resource.DeviceResourceException;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.commands.ICommandImageService;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.commands.IElementReference;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.handlers.HandlerProxy;
import org.eclipse.ui.internal.menus.CommandMessages;
import org.eclipse.ui.internal.misc.StatusUtil;
import org.eclipse.ui.internal.services.IWorkbenchLocationService;
import org.eclipse.ui.keys.IBindingService;
import org.eclipse.ui.services.IServiceLocator;
import org.eclipse.ui.statushandlers.StatusManager;

public class CommandContributionItem extends ContributionItem {
	public static final int STYLE_PUSH = SWT.PUSH;

	public static final int STYLE_CHECK = SWT.CHECK;

	public static final int STYLE_RADIO = SWT.RADIO;

	public static final int STYLE_PULLDOWN = SWT.DROP_DOWN;

	public static int MODE_FORCE_TEXT = 1;

	private LocalResourceManager localResourceManager;

	private Listener menuItemListener;

	private Widget widget;

	private IMenuService menuService;

	private ICommandService commandService;

	private IHandlerService handlerService;

	private IBindingService bindingService;

	private ParameterizedCommand command;

	private ImageDescriptor icon;

	private String label;

	private String tooltip;

	private ImageDescriptor disabledIcon;

	private ImageDescriptor hoverIcon;

	private String mnemonic;

	private IElementReference elementRef;

	private boolean checkedState;

	private int style;

	private ICommandListener commandListener;

	private String dropDownMenuOverride;

	private IWorkbenchHelpSystem workbenchHelpSystem;

	private String helpContextId;

	private int mode = 0;

	private boolean visibleEnabled;

	private Display display;

	private String contributedLabel;

	private String contributedTooltip;

	private ImageDescriptor contributedIcon;

	private ImageDescriptor contributedDisabledIcon;

	private ImageDescriptor contributedHoverIcon;

	private IServiceLocator serviceLocator;

	public CommandContributionItem(
			CommandContributionItemParameter contributionParameters) {
		super(contributionParameters.id);

		contributedLabel = contributionParameters.label;
		contributedTooltip = contributionParameters.tooltip;
		contributedIcon = contributionParameters.icon;
		contributedDisabledIcon = contributionParameters.disabledIcon;
		contributedHoverIcon = contributionParameters.hoverIcon;
		this.serviceLocator = contributionParameters.serviceLocator;


		this.icon = contributionParameters.icon;
		this.disabledIcon = contributionParameters.disabledIcon;
		this.hoverIcon = contributionParameters.hoverIcon;
		this.label = contributionParameters.label;
		this.mnemonic = contributionParameters.mnemonic;
		this.tooltip = contributionParameters.tooltip;
		this.style = contributionParameters.style;
		this.helpContextId = contributionParameters.helpContextId;
		this.visibleEnabled = contributionParameters.visibleEnabled;
		this.mode = contributionParameters.mode;

		menuService = contributionParameters.serviceLocator
				.getService(IMenuService.class);
		commandService = contributionParameters.serviceLocator
				.getService(ICommandService.class);
		handlerService = contributionParameters.serviceLocator
				.getService(IHandlerService.class);
		bindingService = contributionParameters.serviceLocator
				.getService(IBindingService.class);
		IWorkbenchLocationService workbenchLocationService = contributionParameters.serviceLocator.getService(IWorkbenchLocationService.class);
		display = workbenchLocationService.getWorkbench().getDisplay();
		
		createCommand(contributionParameters.commandId,
				contributionParameters.parameters);

		if (command != null) {
				setImages(contributionParameters.serviceLocator,
						contributionParameters.iconStyle);

				if (contributionParameters.helpContextId == null) {
					try {
						this.helpContextId = commandService
								.getHelpContextId(contributionParameters.commandId);
					} catch (NotDefinedException e) {
					}
				}
				IWorkbenchLocationService wls = contributionParameters.serviceLocator
						.getService(IWorkbenchLocationService.class);
				final IWorkbench workbench = wls.getWorkbench();
				if (workbench != null && helpContextId != null) {
					this.workbenchHelpSystem = workbench.getHelpSystem();
				}
		}

	}

	@Deprecated
	public CommandContributionItem(IServiceLocator serviceLocator, String id,
			String commandId, Map parameters, ImageDescriptor icon,
			ImageDescriptor disabledIcon, ImageDescriptor hoverIcon,
			String label, String mnemonic, String tooltip, int style) {
		this(new CommandContributionItemParameter(serviceLocator, id,
				commandId, parameters, icon, disabledIcon, hoverIcon, label,
				mnemonic, tooltip, style, null, false));
	}

	private void setImages(IServiceLocator locator, String iconStyle) {
		if (icon == null) {
			ICommandImageService service = locator
					.getService(ICommandImageService.class);
			icon = service.getImageDescriptor(command.getId(),
					ICommandImageService.TYPE_DEFAULT, iconStyle);
			disabledIcon = service.getImageDescriptor(command.getId(),
					ICommandImageService.TYPE_DISABLED, iconStyle);
			hoverIcon = service.getImageDescriptor(command.getId(),
					ICommandImageService.TYPE_HOVER, iconStyle);

			if (contributedIcon == null)
				contributedIcon = icon;
			if (contributedDisabledIcon == null)
				contributedDisabledIcon = disabledIcon;
			if (contributedHoverIcon == null)
				contributedHoverIcon = hoverIcon;
		}
	}

	private ICommandListener getCommandListener() {
		if (commandListener == null) {
			commandListener = new ICommandListener() {
				@Override
				public void commandChanged(CommandEvent commandEvent) {
					if (commandEvent.isHandledChanged()
							|| commandEvent.isEnabledChanged()
							|| commandEvent.isDefinedChanged()) {
						updateCommandProperties(commandEvent);
					}
				}
			};
		}
		return commandListener;
	}

	private void updateCommandProperties(final CommandEvent commandEvent) {
		if (commandEvent.isHandledChanged()) {
			dropDownMenuOverride = null;
		}
		Runnable update = new Runnable() {
			@Override
			public void run() {
				if (commandEvent.isEnabledChanged()
						|| commandEvent.isHandledChanged()) {
					if (visibleEnabled) {
						IContributionManager parent = getParent();
						if (parent != null) {
							parent.update(true);
						}
					}
					IHandler handler = commandEvent.getCommand().getHandler();
					if (shouldRestoreAppearance(handler)) {
						label = contributedLabel;
						tooltip = contributedTooltip;
						icon = contributedIcon;
						disabledIcon = contributedDisabledIcon;
						hoverIcon = contributedHoverIcon;
					}
				}
				if (commandEvent.getCommand().isDefined()) {
					update(null);
				}
			}
		};
		if (display.getThread() == Thread.currentThread()) {
			update.run();
		} else {
			display.asyncExec(update);
		}
	}

	private boolean shouldRestoreAppearance(IHandler handler) {

		if (handler == null)
			return true;

		if (!(handler instanceof IElementUpdater))
			return true;

		if (handler instanceof HandlerProxy) {
			HandlerProxy handlerProxy = (HandlerProxy) handler;
			IHandler actualHandler = handlerProxy.getHandler();
			return shouldRestoreAppearance(actualHandler);
		}
		return false;
	}

	public ParameterizedCommand getCommand() {
		return command;
	}

	void createCommand(String commandId, Map parameters) {
		if (commandId == null) {
			StatusManager.getManager().handle(
					StatusUtil.newStatus(IStatus.ERROR,
							"Unable to create menu item \"" + getId() //$NON-NLS-1$
									+ "\", no command id", null)); //$NON-NLS-1$
			return;
		}
		Command cmd = commandService.getCommand(commandId);
		if (!cmd.isDefined()) {
			StatusManager
					.getManager()
					.handle(
							StatusUtil
									.newStatus(
											IStatus.ERROR,
											"Unable to create menu item \"" + getId() //$NON-NLS-1$
													+ "\", command \"" + commandId + "\" not defined", null)); //$NON-NLS-1$ //$NON-NLS-2$
			return;
		}
		command = ParameterizedCommand.generateCommand(cmd, parameters);
	}

	@Override
	public void fill(Menu parent, int index) {
		if (command == null) {
			return;
		}
		if (widget != null || parent == null) {
			return;
		}

		int tmpStyle = style;
		if (tmpStyle == STYLE_PULLDOWN)
			tmpStyle = STYLE_PUSH;

		MenuItem item = null;
		if (index >= 0) {
			item = new MenuItem(parent, tmpStyle, index);
		} else {
			item = new MenuItem(parent, tmpStyle);
		}
		item.setData(this);
		if (workbenchHelpSystem != null) {
			workbenchHelpSystem.setHelp(item, helpContextId);
		}
		item.addListener(SWT.Dispose, getItemListener());
		item.addListener(SWT.Selection, getItemListener());
		widget = item;

		update(null);
		updateIcons();

		establishReferences();
	}

	@Override
	public void fill(Composite parent) {
		if (command == null) {
			return;
		}
		if (widget != null || parent == null) {
			return;
		}

		int tmpStyle = style;
		if (tmpStyle == STYLE_PULLDOWN)
			tmpStyle = STYLE_PUSH;

		Button item = new Button(parent, tmpStyle);
		item.setData(this);
		if (workbenchHelpSystem != null) {
			workbenchHelpSystem.setHelp(item, helpContextId);
		}
		item.addListener(SWT.Dispose, getItemListener());
		item.addListener(SWT.Selection, getItemListener());
		widget = item;

		update(null);
		updateIcons();

		establishReferences();
	}

	@Override
	public void fill(ToolBar parent, int index) {
		if (command == null) {
			return;
		}
		if (widget != null || parent == null) {
			return;
		}

		ToolItem item = null;
		if (index >= 0) {
			item = new ToolItem(parent, style, index);
		} else {
			item = new ToolItem(parent, style);
		}

		item.setData(this);

		item.addListener(SWT.Selection, getItemListener());
		item.addListener(SWT.Dispose, getItemListener());
		widget = item;

		update(null);
		updateIcons();

		establishReferences();
	}

	@Override
	public void update() {
		update(null);
	}

	@Override
	public void update(String id) {
		if (widget != null) {
			if (widget instanceof MenuItem) {
				updateMenuItem();
			} else if (widget instanceof ToolItem) {
				updateToolItem();
			} else if (widget instanceof Button) {
				updateButton();
			}
		}
	}

	private void updateMenuItem() {
		MenuItem item = (MenuItem) widget;

		String text = label;
		if (text == null) {
			if (command != null) {
				try {
					text = command.getCommand().getName();
				} catch (NotDefinedException e) {
					StatusManager.getManager().handle(
							StatusUtil.newStatus(IStatus.ERROR,
									"Update item failed " //$NON-NLS-1$
											+ getId(), e));
				}
			}
		}
		text = updateMnemonic(text);

		String keyBindingText = null;
		if (command != null) {
			TriggerSequence binding = bindingService
					.getBestActiveBindingFor(command);
			if (binding != null) {
				keyBindingText = binding.format();
			}
		}
		if (text != null) {
			if (keyBindingText == null) {
				item.setText(text);
			} else {
				item.setText(text + '\t' + keyBindingText);
			}
		}

		if (item.getSelection() != checkedState) {
			item.setSelection(checkedState);
		}

		boolean shouldBeEnabled = isEnabled();
		if (!item.isDisposed() && item.getEnabled() != shouldBeEnabled) {
			item.setEnabled(shouldBeEnabled);
		}
	}

	private void updateToolItem() {
		ToolItem item = (ToolItem) widget;

		String text = label;
		String tooltip = label;

		if (text == null) {
			if (command != null) {
				try {
					text = command.getCommand().getName();
					tooltip = command.getCommand().getDescription();
					if (tooltip == null || tooltip.trim().length() == 0) {
						tooltip = text;
					}
				} catch (NotDefinedException e) {
					StatusManager.getManager().handle(
							StatusUtil.newStatus(IStatus.ERROR,
									"Update item failed " //$NON-NLS-1$
											+ getId(), e));
				}
			}
		}

		if ((icon == null || (mode & MODE_FORCE_TEXT) == MODE_FORCE_TEXT)
				&& text != null) {
			item.setText(text);
		}

		String toolTipText = getToolTipText(tooltip);
		item.setToolTipText(toolTipText);

		if (item.getSelection() != checkedState) {
			item.setSelection(checkedState);
		}

		boolean shouldBeEnabled = isEnabled();
		if (!item.isDisposed() && item.getEnabled() != shouldBeEnabled) {
			item.setEnabled(shouldBeEnabled);
		}
	}

	private void updateButton() {
		Button item = (Button) widget;

		String text = label;
		if (text == null) {
			if (command != null) {
				try {
					text = command.getCommand().getName();
				} catch (NotDefinedException e) {
					StatusManager.getManager().handle(
							StatusUtil.newStatus(IStatus.ERROR,
									"Update item failed " //$NON-NLS-1$
											+ getId(), e));
				}
			}
		}

		if (text != null) {
			item.setText(text);
		}

		String toolTipText = getToolTipText(text);
		item.setToolTipText(toolTipText);

		if (item.getSelection() != checkedState) {
			item.setSelection(checkedState);
		}

		boolean shouldBeEnabled = isEnabled();
		if (!item.isDisposed() && item.getEnabled() != shouldBeEnabled) {
			item.setEnabled(shouldBeEnabled);
		}
	}

	private String getToolTipText(String text) {
		String tooltipText = tooltip;
		if (tooltip == null)
			if (text != null)
				tooltipText = text;
			else
				tooltipText = ""; //$NON-NLS-1$

		TriggerSequence activeBinding = bindingService
				.getBestActiveBindingFor(command);
		if (activeBinding != null && !activeBinding.isEmpty()) {
			String acceleratorText = activeBinding.format();
			if (acceleratorText != null
					&& acceleratorText.length() != 0) {
				tooltipText = NLS.bind(CommandMessages.Tooltip_Accelerator,
						tooltipText, acceleratorText);
			}
		}

		return tooltipText;
	}

	private String updateMnemonic(String s) {
		if (mnemonic == null || s == null) {
			return s;
		}
		int idx = s.indexOf(mnemonic);
		if (idx == -1) {
			return s;
		}

		return s.substring(0, idx) + '&' + s.substring(idx);
	}

	private void handleWidgetDispose(Event event) {
		if (event.widget == widget) {
			disconnectReferences();
			widget.removeListener(SWT.Selection, getItemListener());
			widget.removeListener(SWT.Dispose, getItemListener());
			widget = null;
			disposeOldImages();
		}
	}

	@Override
	public void setParent(IContributionManager parent) {
		super.setParent(parent);
		if (parent == null)
			disconnectReferences();
	}

	private void establishReferences() {
		if (command != null) {
			UIElement callback = new UIElement(serviceLocator) {
	
				@Override
				public void setChecked(boolean checked) {
					CommandContributionItem.this.setChecked(checked);
				}
	
				@Override
				public void setDisabledIcon(ImageDescriptor desc) {
					CommandContributionItem.this.setDisabledIcon(desc);
				}
	
				@Override
				public void setHoverIcon(ImageDescriptor desc) {
					CommandContributionItem.this.setHoverIcon(desc);
				}
	
				@Override
				public void setIcon(ImageDescriptor desc) {
					CommandContributionItem.this.setIcon(desc);
				}
	
				@Override
				public void setText(String text) {
					CommandContributionItem.this.setText(text);
				}
	
				@Override
				public void setTooltip(String text) {
					CommandContributionItem.this.setTooltip(text);
				}
	
				@Override
				public void setDropDownId(String id) {
					dropDownMenuOverride = id;
				}
			};
			try {
				elementRef = commandService.registerElementForCommand(command, callback);
			} catch (NotDefinedException e) {
				StatusManager.getManager().handle(
						StatusUtil.newStatus(IStatus.ERROR, "Unable to register menu item \"" + getId() //$NON-NLS-1$
								+ "\", command \"" + command.getId() + "\" not defined", //$NON-NLS-1$ //$NON-NLS-2$
								null));
			}
			command.getCommand().addCommandListener(getCommandListener());
		}
		bindingService.addBindingManagerListener(bindingManagerListener);
	}

	private void disconnectReferences() {
		if (elementRef != null) {
			commandService.unregisterElement(elementRef);
			elementRef = null;
		}
		if (commandListener != null) {
			command.getCommand().removeCommandListener(commandListener);
			commandListener = null;
		}

		if (bindingService != null) {
			bindingService.removeBindingManagerListener(bindingManagerListener);
		}
	}

	@Override
	public void dispose() {
		if (widget != null) {
			widget.dispose();
			widget = null;
		}

		disconnectReferences();

		command = null;
		commandService = null;
		bindingService = null;
		menuService = null;
		handlerService = null;
		disposeOldImages();
		super.dispose();
	}

	private void disposeOldImages() {
		if (localResourceManager != null) {
			localResourceManager.dispose();
			localResourceManager = null;
		}
	}

	private Listener getItemListener() {
		if (menuItemListener == null) {
			menuItemListener = new Listener() {
				@Override
				public void handleEvent(Event event) {
					switch (event.type) {
					case SWT.Dispose:
						handleWidgetDispose(event);
						break;
					case SWT.Selection:
						if (event.widget != null) {
							handleWidgetSelection(event);
						}
						break;
					}
				}
			};
		}
		return menuItemListener;
	}

	private void handleWidgetSelection(Event event) {
		if (openDropDownMenu(event))
			return;

		if ((style & (SWT.TOGGLE | SWT.CHECK)) != 0) {
			if (event.widget instanceof ToolItem) {
				checkedState = ((ToolItem) event.widget).getSelection();
			} else if (event.widget instanceof MenuItem) {
				checkedState = ((MenuItem) event.widget).getSelection();
			}
		}

		try {
			handlerService.executeCommand(command, event);
		} catch (ExecutionException e) {
			StatusManager.getManager().handle(
					StatusUtil.newStatus(IStatus.ERROR,
							"Failed to execute item " //$NON-NLS-1$
									+ getId(), e));
		} catch (NotDefinedException e) {
			StatusManager.getManager().handle(
					StatusUtil.newStatus(IStatus.ERROR,
							"Failed to execute item " //$NON-NLS-1$
									+ getId(), e));
		} catch (NotEnabledException e) {
			StatusManager.getManager().handle(
					StatusUtil.newStatus(IStatus.ERROR,
							"Failed to execute item " //$NON-NLS-1$
									+ getId(), e));
		} catch (NotHandledException e) {
			StatusManager.getManager().handle(
					StatusUtil.newStatus(IStatus.ERROR,
							"Failed to execute item " //$NON-NLS-1$
									+ getId(), e));
		}
	}

	private boolean openDropDownMenu(Event event) {
		Widget item = event.widget;
		if (item != null) {
			int style = item.getStyle();
			if ((style & SWT.DROP_DOWN) != 0) {
				if (event.detail == 4) { // on drop-down button
					ToolItem ti = (ToolItem) item;

					final MenuManager menuManager = new MenuManager();
					Menu menu = menuManager.createContextMenu(ti.getParent());
					if (workbenchHelpSystem != null) {
						workbenchHelpSystem.setHelp(menu, helpContextId);
					}
					menuManager.addMenuListener(new IMenuListener2() {
						@Override
						public void menuAboutToShow(IMenuManager manager) {
							String id = getId();
							if (dropDownMenuOverride != null) {
								id = dropDownMenuOverride;
							}
							menuService.populateContributionManager(
									menuManager, "menu:" + id); //$NON-NLS-1$
						}
						@Override
						public void menuAboutToHide(IMenuManager manager) {
							display.asyncExec(new Runnable() {
								@Override
								public void run() {
									menuService.releaseContributions(menuManager);
									menuManager.dispose();
								}
							});
						}
					});

					Point point = ti.getParent().toDisplay(
							new Point(event.x, event.y));
					menu.setLocation(point.x, point.y); // waiting for SWT
					menu.setVisible(true);
					return true; // we don't fire the action
				}
			}
		}

		return false;
	}

	private void setIcon(ImageDescriptor desc) {
		icon = desc;
		updateIcons();
	}

	private void updateIcons() {
		if (widget instanceof MenuItem) {
			MenuItem item = (MenuItem) widget;
			LocalResourceManager m = new LocalResourceManager(JFaceResources
					.getResources());
			try {
				item.setImage(icon == null ? null : m.createImage(icon));
			} catch (DeviceResourceException e) {
				icon = ImageDescriptor.getMissingImageDescriptor();
				item.setImage(m.createImage(icon));
				StatusManager.getManager().handle(
						new Status(IStatus.ERROR, WorkbenchPlugin.PI_WORKBENCH,
								"Failed to load image", e)); //$NON-NLS-1$
			}
			disposeOldImages();
			localResourceManager = m;
		} else if (widget instanceof ToolItem) {
			ToolItem item = (ToolItem) widget;
			LocalResourceManager m = new LocalResourceManager(JFaceResources
					.getResources());
			item.setDisabledImage(disabledIcon == null ? null : m
					.createImage(disabledIcon));
			item.setHotImage(hoverIcon == null ? null : m
					.createImage(hoverIcon));
			item.setImage(icon == null ? null : m.createImage(icon));
			disposeOldImages();
			localResourceManager = m;
		}
	}

	private void setText(String text) {
		label = text;
		update(null);
	}

	private void setChecked(boolean checked) {
		if (checkedState == checked) {
			return;
		}
		checkedState = checked;
		if (widget instanceof MenuItem) {
			((MenuItem) widget).setSelection(checkedState);
		} else if (widget instanceof ToolItem) {
			((ToolItem) widget).setSelection(checkedState);
		}
	}

	private void setTooltip(String text) {
		tooltip = text;
		if (widget instanceof ToolItem) {
			((ToolItem) widget).setToolTipText(text);
		}
	}

	private void setDisabledIcon(ImageDescriptor desc) {
		disabledIcon = desc;
		updateIcons();
	}

	private void setHoverIcon(ImageDescriptor desc) {
		hoverIcon = desc;
		updateIcons();
	}

	@Override
	public boolean isEnabled() {
		if (command != null) {
			command.getCommand().setEnabled(menuService.getCurrentState());
			return command.getCommand().isEnabled();
		}
		return false;
	}

	@Override
	public boolean isVisible() {
		if (visibleEnabled) {
			return super.isVisible() && isEnabled();
		}
		return super.isVisible();
	}

	private IBindingManagerListener bindingManagerListener = new IBindingManagerListener() {

		@Override
		public void bindingManagerChanged(BindingManagerEvent event) {
			if (event.isActiveBindingsChanged()
					&& event.isActiveBindingsChangedFor(getCommand())) {
				update();
			}

		}

	};

	public CommandContributionItemParameter getData() {
		CommandContributionItemParameter data = new CommandContributionItemParameter(
				serviceLocator, getId(), null, style);
		data.icon = contributedIcon;
		data.disabledIcon = contributedDisabledIcon;
		data.hoverIcon = contributedHoverIcon;
		data.label = contributedLabel;
		data.tooltip = contributedTooltip;
		data.helpContextId = helpContextId;
		data.mnemonic = mnemonic;
		return data;
	}

}
