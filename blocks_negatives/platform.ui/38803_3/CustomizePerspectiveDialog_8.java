			IContributionItem contributionItem = toolbarMngrRenderer.getContribution(element);
			if (element instanceof MToolBarSeparator
					|| (contributionItem == null || contributionItem.isGroupMarker() || contributionItem
							.isSeparator())) {
				continue;
			}

			if (OpaqueElementUtil.isOpaqueToolItem(element)) {
				if (contributionItem instanceof ActionContributionItem) {
					final IAction action = ((ActionContributionItem) contributionItem).getAction();
					DisplayItem toolbarEntry = new DisplayItem(action.getText(), contributionItem);
					toolbarEntry.setImageDescriptor(action.getImageDescriptor());
					toolbarEntry.setActionSet(idToActionSet.get(getActionSetID(contributionItem)));
					if (toolbarEntry.getChildren().isEmpty()) {
						toolbarEntry.setCheckState(getToolbarItemIsVisible(toolbarEntry));
					}
					parent.addChild(toolbarEntry);
				}
			} else {
				String text = null;
				if (element instanceof MItem) {
					text = getToolTipText((MItem) element);
				}
				ImageDescriptor iconDescriptor = null;
				String iconURI = element instanceof MItem ? ((MItem) element).getIconURI() : null;
				if (iconURI != null && iconURI.length() > 0) {
					iconDescriptor = resUtils.imageDescriptorFromURI(URI.createURI(iconURI));
				}
				if (element.getWidget() instanceof ToolItem) {
					ToolItem item = (ToolItem) element.getWidget();
					if (text == null) {
						text = item.getToolTipText();
					}
					if (iconDescriptor == null) {
						Image image = item.getImage();
						if (image != null) {
							iconDescriptor = ImageDescriptor.createFromImage(image);
						}
					}
				}
				if (text == null) {
						text = (String) name;
					} else {
						text = getToolbarLabel(element.getElementId());
					}
				}
