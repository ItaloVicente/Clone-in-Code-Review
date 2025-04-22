
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
