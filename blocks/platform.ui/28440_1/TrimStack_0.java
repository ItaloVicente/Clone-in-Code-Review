			if (hasRenderedElements) {
				for (MUIElement stackElement : theStack.getChildren()) {
					if (!stackElement.isToBeRendered()) {
						continue;
					}

					MUILabel labelElement = getLabelElement(stackElement);
					ToolItem newItem = new ToolItem(trimStackTB, SWT.CHECK);
					newItem.setData(labelElement);
					newItem.setImage(getImage(labelElement));
					newItem.setToolTipText(getLabelText(labelElement));
					newItem.addSelectionListener(toolItemSelectionListener);
				}
			} else if (theStack.getTags().contains(IPresentationEngine.NO_AUTO_COLLAPSE)) {
				ToolItem ti = new ToolItem(trimStackTB, SWT.CHECK);
				ti.setToolTipText(Messages.TrimStack_EmptyStackTooltip);
				ti.setImage(getLayoutImage());
				ti.addSelectionListener(toolItemSelectionListener);
			} else {
