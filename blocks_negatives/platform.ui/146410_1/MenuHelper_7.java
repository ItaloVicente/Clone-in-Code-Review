	public static MToolItem createToolItem(MApplication application, ActionContributionItem item) {
		final IAction action = item.getAction();
		String id = action.getActionDefinitionId();
		if (id != null) {
			for (MCommand command : application.getCommands()) {
				if (id.equals(command.getElementId())) {
					MHandledToolItem toolItem = MenuFactoryImpl.eINSTANCE.createHandledToolItem();
					toolItem.setCommand(command);
					toolItem.setContributorURI(command.getContributorURI());
					toolItem.setVisible(item.isVisible());

					String iconURI = getIconURI(action.getImageDescriptor(), application.getContext());
					if (iconURI == null) {
						iconURI = getIconURI(id, application.getContext(), ICommandImageService.TYPE_DEFAULT);
						if (iconURI == null) {
							toolItem.setLabel(command.getCommandName());
						} else {
							toolItem.setIconURI(iconURI);
						}
					} else {
						toolItem.setIconURI(iconURI);
					}
					if (action.getToolTipText() != null) {
						toolItem.setTooltip(action.getToolTipText());
					}

					String disabledIconURI = getIconURI(action.getDisabledImageDescriptor(), application.getContext());
					if (disabledIconURI == null)
						disabledIconURI = getIconURI(id, application.getContext(), ICommandImageService.TYPE_DEFAULT);
					if (disabledIconURI != null)
						setDisabledIconURI(toolItem, disabledIconURI);

					switch (action.getStyle()) {
					case IAction.AS_CHECK_BOX:
						toolItem.setType(ItemType.CHECK);
						toolItem.setSelected(action.isChecked());
						break;
					case IAction.AS_RADIO_BUTTON:
						toolItem.setType(ItemType.RADIO);
						toolItem.setSelected(action.isChecked());
						break;
					default:
						toolItem.setType(ItemType.PUSH);
						break;
					}
					String itemId = item.getId();
					toolItem.setElementId(itemId == null ? id : itemId);
					return toolItem;
				}
			}
		} else {
			final MDirectToolItem toolItem = MenuFactoryImpl.eINSTANCE.createDirectToolItem();
			String itemId = item.getId();
			toolItem.setElementId(itemId);
			toolItem.setVisible(item.isVisible());
			String iconURI = getIconURI(action.getImageDescriptor(), application.getContext());
			if (iconURI == null) {
				if (itemId == null) {
					if (action.getText() != null) {
						toolItem.setLabel(action.getText());
					}
				} else {
					iconURI = getIconURI(itemId, application.getContext(), ICommandImageService.TYPE_DEFAULT);
					if (iconURI == null) {
						if (action.getText() != null) {
							toolItem.setLabel(action.getText());
						}
					} else {
						toolItem.setIconURI(iconURI);
					}
				}
			} else {
				toolItem.setIconURI(iconURI);
			}
			if (action.getToolTipText() != null) {
				toolItem.setTooltip(action.getToolTipText());
			}

			switch (action.getStyle()) {
			case IAction.AS_CHECK_BOX:
				toolItem.setType(ItemType.CHECK);
				toolItem.setSelected(action.isChecked());
				break;
			case IAction.AS_RADIO_BUTTON:
				toolItem.setType(ItemType.RADIO);
				toolItem.setSelected(action.isChecked());
				break;
			default:
				toolItem.setType(ItemType.PUSH);
				break;
			}
			toolItem.setObject(new DirectProxy(action));
			toolItem.setEnabled(action.isEnabled());

			final IPropertyChangeListener propertyListener = event -> {
				String property = event.getProperty();
				if (property.equals(IAction.ENABLED)) {
					toolItem.setEnabled(action.isEnabled());
				} else if (property.equals(IAction.CHECKED)) {
					toolItem.setSelected(action.isChecked());
				} else if (property.equals(IAction.TEXT)) {
					toolItem.setLabel(action.getText());
				} else if (property.equals(IAction.TOOL_TIP_TEXT)) {
					toolItem.setLabel(action.getToolTipText());
				}
			};
			action.addPropertyChangeListener(propertyListener);
			toolItem.getTransientData().put(AbstractContributionItem.DISPOSABLE,
					(Runnable) () -> action.removePropertyChangeListener(propertyListener));
			return toolItem;
		}
		return null;
	}

	public static MMenuItem createItem(MApplication application, ActionContributionItem item) {
		IAction action = item.getAction();
		String id = action.getActionDefinitionId();
		if (action instanceof OpenPreferencesAction) {
			for (MCommand command : application.getCommands()) {
				if (IWorkbenchCommandConstants.WINDOW_PREFERENCES.equals(command.getElementId())) {
					MHandledMenuItem menuItem = MenuFactoryImpl.eINSTANCE.createHandledMenuItem();
					menuItem.setCommand(command);
					menuItem.setLabel(command.getCommandName());
					menuItem.setIconURI(getIconURI(action.getImageDescriptor(), application.getContext()));

					String text = action.getText();
					int index = text.indexOf('&');
					if (index != -1 && index != text.length() - 1) {
						menuItem.setMnemonics(text.substring(index + 1, index + 2));
					}

					switch (action.getStyle()) {
					case IAction.AS_CHECK_BOX:
						menuItem.setType(ItemType.CHECK);
						menuItem.setSelected(action.isChecked());
						break;
					case IAction.AS_RADIO_BUTTON:
						menuItem.setType(ItemType.RADIO);
						menuItem.setSelected(action.isChecked());
						break;
					default:
						menuItem.setType(ItemType.PUSH);
						break;
					}

					String itemId = item.getId();
					menuItem.setElementId(itemId == null ? id : itemId);
					return menuItem;
				}
			}
		} else if (id != null) {

			for (MCommand command : application.getCommands()) {
				if (id.equals(command.getElementId())) {
					MHandledMenuItem menuItem = MenuFactoryImpl.eINSTANCE.createHandledMenuItem();
					menuItem.setCommand(command);
					if (action.getText() != null) {
						menuItem.setLabel(action.getText());
					} else {
						menuItem.setLabel(command.getCommandName());
					}
					menuItem.setIconURI(getIconURI(action.getImageDescriptor(), application.getContext()));

					switch (action.getStyle()) {
					case IAction.AS_CHECK_BOX:
						menuItem.setType(ItemType.CHECK);
						menuItem.setSelected(action.isChecked());
						break;
					case IAction.AS_RADIO_BUTTON:
						menuItem.setType(ItemType.RADIO);
						menuItem.setSelected(action.isChecked());
						break;
					default:
						menuItem.setType(ItemType.PUSH);
						break;
					}

					String itemId = item.getId();
					menuItem.setElementId(itemId == null ? id : itemId);
					return menuItem;
				}
			}
		} else {
			MDirectMenuItem menuItem = MenuFactoryImpl.eINSTANCE.createDirectMenuItem();
			if (action.getText() != null) {
				menuItem.setLabel(action.getText());
			}
			String itemId = item.getId();
			menuItem.setElementId(itemId == null ? id : itemId);
			menuItem.setIconURI(getIconURI(action.getImageDescriptor(), application.getContext()));
			switch (action.getStyle()) {
			case IAction.AS_CHECK_BOX:
				menuItem.setType(ItemType.CHECK);
				menuItem.setSelected(action.isChecked());
				break;
			case IAction.AS_RADIO_BUTTON:
				menuItem.setType(ItemType.RADIO);
				menuItem.setSelected(action.isChecked());
				break;
			default:
				menuItem.setType(ItemType.PUSH);
				break;
			}
			menuItem.setObject(new DirectProxy(action));
			return menuItem;
		}
		return null;
	}

	static class DirectProxy {
		private IAction action;

		public DirectProxy(IAction action) {
			this.action = action;
		}

		@CanExecute
		public boolean canExecute() {
			return action.isEnabled();
		}

		@Execute
		public void execute() {
			action.run();
		}
	}

