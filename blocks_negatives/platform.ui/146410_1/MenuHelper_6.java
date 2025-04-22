	public static MMenuElement createLegacyMenuActionAdditions(MApplication app, final IConfigurationElement element) {
		final String id = MenuHelper.getId(element);
		String text = MenuHelper.getLabel(element);
		String mnemonic = MenuHelper.getMnemonic(element);
		if (text != null && mnemonic != null) {
			int idx = text.indexOf(mnemonic);
			if (idx != -1) {
				text = text.substring(0, idx) + '&' + text.substring(idx);
			}
		}
		String iconUri = MenuHelper.getIconURI(element, IWorkbenchRegistryConstants.ATT_ICON);
		final String cmdId = MenuHelper.getActionSetCommandId(element);

		MCommand cmd = ContributionsAnalyzer.getCommandById(app, cmdId);
		if (cmd == null) {
			ECommandService commandService = app.getContext().get(ECommandService.class);
			Command command = commandService.getCommand(cmdId);
			if (command == null) {
				ICommandService ics = app.getContext().get(ICommandService.class);
				command = commandService.defineCommand(cmdId, text, null, ics.getCategory(null), null);
			}
			cmd = CommandsFactoryImpl.eINSTANCE.createCommand();
			cmd.setCommandName(text);
			cmd.setElementId(cmdId);
			app.getCommands().add(cmd);
		}

		String style = element.getAttribute(IWorkbenchRegistryConstants.ATT_STYLE);
			MMenuItem item = RenderedElementUtil.createRenderedMenuItem();
			item.setLabel(text);
			if (iconUri != null) {
				item.setIconURI(iconUri);
			}
			IContextFunction generator = new ContextFunction() {
				@Override
				public Object compute(IEclipseContext context, String contextKey) {
					IWorkbenchWindow window = context.get(IWorkbenchWindow.class);
					if (window == null) {
						return null;
					}
					ActionDescriptor desc = new ActionDescriptor(element, ActionDescriptor.T_WORKBENCH_PULLDOWN,
							window);
					final PluginAction action = desc.getAction();
					return new ActionContributionItem(action) {
						@Override
						public void dispose() {
							super.dispose();
							action.disposeDelegate();
						}
					};
				}
			};
			RenderedElementUtil.setContributionManager(item, generator);
			return item;
		}

		ItemType type = ItemType.PUSH;
		if (IWorkbenchRegistryConstants.STYLE_TOGGLE.equals(style)) {
			type = ItemType.CHECK;
		} else if (IWorkbenchRegistryConstants.STYLE_RADIO.equals(style)) {
			type = ItemType.RADIO;
		}
		MHandledMenuItem item = MenuFactoryImpl.eINSTANCE.createHandledMenuItem();
		item.setElementId(id);
		item.setLabel(text);
		item.setType(type);
		item.setCommand(cmd);
		if (iconUri != null) {
			item.setIconURI(iconUri);
		}
		return item;
	}

	public static String getDescription(IConfigurationElement configElement) {
		return configElement.getAttribute(IWorkbenchRegistryConstants.TAG_DESCRIPTION);
	}

	public static MToolBarElement createLegacyToolBarActionAdditions(MApplication app,
			final IConfigurationElement element) {
		String cmdId = MenuHelper.getActionSetCommandId(element);
		final String id = MenuHelper.getId(element);
		String text = MenuHelper.getLabel(element);
		String mnemonic = MenuHelper.getMnemonic(element);
		if (text != null && mnemonic != null) {
			int idx = text.indexOf(mnemonic);
			if (idx != -1) {
				text = text.substring(0, idx) + '&' + text.substring(idx);
			}
		}
		String iconUri = MenuHelper.getIconURI(element, IWorkbenchRegistryConstants.ATT_ICON);
		String disabledIconUri = MenuHelper.getIconURI(element, IWorkbenchRegistryConstants.ATT_DISABLEDICON);
		MCommand cmd = ContributionsAnalyzer.getCommandById(app, cmdId);
		if (cmd == null) {
			ECommandService commandService = app.getContext().get(ECommandService.class);
			Command command = commandService.getCommand(cmdId);
			if (command == null) {
				ICommandService ics = app.getContext().get(ICommandService.class);
				command = commandService.defineCommand(cmdId, text, null, ics.getCategory(null), null);
			}
			cmd = CommandsFactoryImpl.eINSTANCE.createCommand();
			cmd.setCommandName(text);
			cmd.setElementId(cmdId);
			app.getCommands().add(cmd);
		}
		final MHandledToolItem item = MenuFactoryImpl.eINSTANCE.createHandledToolItem();

		String style = element.getAttribute(IWorkbenchRegistryConstants.ATT_STYLE);
		if (IWorkbenchRegistryConstants.STYLE_TOGGLE.equals(style)) {
			item.setType(ItemType.CHECK);
			IContextFunction generator = createToggleFunction(element);
			if (generator != null) {
				item.getTransientData().put(ItemType.CHECK.toString(), generator);
			}
		} else if (IWorkbenchRegistryConstants.STYLE_RADIO.equals(style)) {
			item.setType(ItemType.RADIO);
		} else {
			item.setType(ItemType.PUSH);
		}

			MMenu menu = RenderedElementUtil.createRenderedMenu();
			ECommandService cs = app.getContext().get(ECommandService.class);
			final ParameterizedCommand parmCmd = cs.createCommand(cmdId, null);
			IContextFunction generator = new ContextFunction() {
				@Override
				public Object compute(IEclipseContext context, String contextKey) {
					return new IMenuCreator() {
						private ActionDelegateHandlerProxy handlerProxy;

						private ActionDelegateHandlerProxy getProxy() {
							if (handlerProxy == null) {
								handlerProxy = new ActionDelegateHandlerProxy(element,
										IWorkbenchRegistryConstants.ATT_CLASS, id, parmCmd,
										PlatformUI.getWorkbench().getActiveWorkbenchWindow(), null, null, null);
							}
							return handlerProxy;
						}

						private IWorkbenchWindowPulldownDelegate getDelegate() {
							getProxy();
							if (handlerProxy == null) {
								return null;
							}
							if (handlerProxy.getDelegate() == null) {
								handlerProxy.loadDelegate();

								ISelectionService service = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
										.getSelectionService();
								IActionDelegate delegate = handlerProxy.getDelegate();
								delegate.selectionChanged(handlerProxy.getAction(), service.getSelection());
							}
							return (IWorkbenchWindowPulldownDelegate) handlerProxy.getDelegate();
						}

						@Override
						public Menu getMenu(Menu parent) {
							IWorkbenchWindowPulldownDelegate2 delegate = (IWorkbenchWindowPulldownDelegate2) getDelegate();
							if (delegate == null) {
								return null;
							}
							return delegate.getMenu(parent);
						}

						@Override
						public Menu getMenu(Control parent) {
							return getDelegate() == null ? null : getDelegate().getMenu(parent);
						}

						@Override
						public void dispose() {
							if (handlerProxy != null) {
								handlerProxy.dispose();
								handlerProxy = null;
							}
						}
					};
				}
			};
			RenderedElementUtil.setContributionManager(menu, generator);
			item.setMenu(menu);
		}

		item.setElementId(id);
		item.setCommand(cmd);
		if (iconUri == null) {
			item.setLabel(text);
		} else {
			item.setIconURI(iconUri);
		}
		if (disabledIconUri != null) {
			setDisabledIconURI(item, disabledIconUri);
		}
		String tooltip = getTooltip(element);
		item.setTooltip(tooltip == null ? text : tooltip);
		return item;
	}

	private static int getType(String name) {
		if (name.equals(IWorkbenchRegistryConstants.TAG_ACTION_SET)) {
			return ActionDescriptor.T_WORKBENCH;
		} else if (name.equals(IWorkbenchRegistryConstants.TAG_VIEW_CONTRIBUTION)) {
			return ActionDescriptor.T_VIEW;
		} else if (name.equals(IWorkbenchRegistryConstants.TAG_EDITOR_CONTRIBUTION)) {
			return ActionDescriptor.T_EDITOR;
		}
		return -1;
	}

	private static IContextFunction createToggleFunction(final IConfigurationElement element) {
		Object ice = element.getParent();
		if (!(ice instanceof IConfigurationElement)) {
			return null;
		}

		IConfigurationElement parent = (IConfigurationElement) ice;
		final int type = getType(parent.getName());
		if (type == -1) {
			return null;
		}

		return new ContextFunction() {
			private ActionDescriptor getDescriptor(IEclipseContext context) {
				switch (type) {
				case ActionDescriptor.T_WORKBENCH:
					IWorkbenchWindow window = context.get(IWorkbenchWindow.class);
					return window == null ? null : new ActionDescriptor(element, type, window);
				case ActionDescriptor.T_EDITOR:
					return new ActionDescriptor(element, type, null);
				case ActionDescriptor.T_VIEW:
					MPart part = context.get(MPart.class);
					if (part != null) {
						Object object = part.getObject();
						if (object instanceof CompatibilityPart) {
							return new ActionDescriptor(element, type, ((CompatibilityPart) object).getPart());
						}
					}
					return null;
				default:
					return null;
				}
			}

			@Override
			public Object compute(IEclipseContext context, String contextKey) {
				final MHandledItem model = context.get(MHandledItem.class);
				if (model == null) {
					return null;
				}
				ActionDescriptor desc = getDescriptor(context);
				final IAction action = desc.getAction();
				final IPropertyChangeListener propListener = event -> {
					if (IAction.CHECKED.equals(event.getProperty())) {
						boolean checked = false;
						if (event.getNewValue() instanceof Boolean) {
							checked = ((Boolean) event.getNewValue()).booleanValue();
						}
						model.setSelected(checked);
					}
				};
				action.addPropertyChangeListener(propListener);
				Runnable obj = () -> action.removePropertyChangeListener(propListener);
				model.setSelected(action.isChecked());
				return obj;
			}
		};
	}

