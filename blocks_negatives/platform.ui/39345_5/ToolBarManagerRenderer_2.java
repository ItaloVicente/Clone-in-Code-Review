			MToolBarElement itemModel = (MToolBarElement) event
					.getProperty(UIEvents.EventTags.ELEMENT);
			String attName = (String) event
					.getProperty(UIEvents.EventTags.ATTNAME);
			if (UIEvents.UIElement.TOBERENDERED.equals(attName)) {
				Object obj = itemModel.getParent();
				if (!(obj instanceof MToolBar)) {
					return;
				}
				ToolBarManager parent = getManager((MToolBar) obj);
				if (itemModel.isToBeRendered()) {
					if (parent != null) {
						modelProcessSwitch(parent, itemModel);
						parent.update(true);
						ToolBar tb = parent.getControl();
						if (tb != null && !tb.isDisposed()) {
							tb.pack(true);
							tb.getShell().layout(new Control[] { tb },
									SWT.DEFER);
						}
					}
				} else {
					IContributionItem ici = modelToContribution
							.remove(itemModel);
					if (ici != null && parent != null) {
						parent.remove(ici);
					}
					if (ici != null) {
						ici.dispose();
					}
				}
			} else if (UIEvents.UIElement.VISIBLE.equals(attName)) {
				IContributionItem ici = getContribution(itemModel);
				if (ici == null) {
					return;
				}
				ici.setVisible(itemModel.isVisible());

				ToolBarManager parent = null;
				if (ici instanceof MenuManager) {
					parent = (ToolBarManager) ((MenuManager) ici).getParent();
				} else if (ici instanceof ContributionItem) {
					parent = (ToolBarManager) ((ContributionItem) ici)
							.getParent();
				}
