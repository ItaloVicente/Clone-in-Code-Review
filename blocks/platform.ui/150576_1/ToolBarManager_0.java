			for (ToolItem toolItem : toolBar.getItems()) {
				Object data = toolItem.getData();
				if (data instanceof ControlContribution) {
					ControlContribution contribution = (ControlContribution) data;
					toolItem.setWidth(contribution.computeWidth(toolItem.getControl()));
				}
			}

