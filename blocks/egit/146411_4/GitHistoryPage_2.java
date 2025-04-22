		private static class FilterAction extends DropDownMenuAction {

			private final @NonNull List<IAction> actions;

			private final IPropertyChangeListener listener;

			private boolean childEnablement = true;

			public FilterAction(IAction... actions) {
				super(UIText.GitHistoryPage_FilterSubMenuLabel);
				@SuppressWarnings("null")
				@NonNull
				List<IAction> a = actions == null ? Collections.emptyList()
						: Arrays.asList(actions);
				this.actions = a;
				setToolTipText(UIText.GitHistoryPage_FilterTooltip);
				listener = e -> {
					if (IAction.ENABLED.equals(e.getProperty())) {
						boolean previousEnablement = isEnabled();
						childEnablement = FilterAction.this.actions.stream()
								.anyMatch(act -> act.isEnabled());
						boolean currentEnablement = isEnabled();
						if (currentEnablement != previousEnablement) {
							IAction currentChild = currentEnablement
									? FilterAction.this.actions.stream()
											.filter(act -> act.isChecked())
											.findFirst().orElse(null)
									: null;
							if (currentChild == null) {
								setToolTipText(
										UIText.GitHistoryPage_FilterTooltip);
							} else {
								setToolTipText(MessageFormat.format(
										UIText.GitHistoryPage_FilterTooltipCurrent,
										currentChild.getToolTipText()));
							}
							firePropertyChange(IAction.ENABLED,
									Boolean.valueOf(previousEnablement),
									Boolean.valueOf(currentEnablement));
						}
					} else if (IAction.CHECKED.equals(e.getProperty())) {
						Object newValue = e.getNewValue();
						boolean isChecked = false;
						if (newValue instanceof Boolean) {
							isChecked = ((Boolean) newValue).booleanValue();
						} else if (newValue instanceof String) {
							isChecked = Boolean.parseBoolean((String) newValue);
						}
						if (isChecked) {
							Object source = e.getSource();
							if (source instanceof IAction) {
								if (isEnabled()) {
									setToolTipText(MessageFormat.format(
											UIText.GitHistoryPage_FilterTooltipCurrent,
											((IAction) source)
													.getToolTipText()));
								}
								ImageDescriptor image = ((IAction) source)
										.getImageDescriptor();
								if (image != null) {
									setImageDescriptor(image);
								}
							}
						}
					}
				};
				for (IAction action : this.actions) {
					action.addPropertyChangeListener(listener);
				}
			}

			@Override
			protected Collection<IAction> getActions() {
				return actions;
			}

			@Override
			public boolean isEnabled() {
				return super.isEnabled() && childEnablement;
			}

			@Override
			public void dispose() {
				for (IAction action : this.actions) {
					action.removePropertyChangeListener(listener);
				}
				super.dispose();
			}

			@Override
			public void run() {
				if (!isEnabled()) {
					return;
				}
				int i = 1;
				for (IAction action : actions) {
					if (action.isChecked()) {
						IAction next = actions.get(i % actions.size());
						while (next != action) {
							if (next.isEnabled()) {
								action.setChecked(false);
								next.setChecked(true);
								next.run();
								break;
							}
							i++;
							next = actions.get(i % actions.size());
						}
						return;
					}
					i++;
				}
			}
		}

