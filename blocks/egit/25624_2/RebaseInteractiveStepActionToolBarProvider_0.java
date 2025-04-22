
			ElementAction typeToSet = type;
			if (type != ElementAction.PICK) {
				boolean allItemsHaveTargetType = true;
				for (RebaseInteractivePlan.PlanElement element : selected)
					allItemsHaveTargetType &= element.getPlanElementAction() == type;
				if (allItemsHaveTargetType) {
					typeToSet = ElementAction.PICK;
					itemPick.setSelection(true);
					if (e.getSource() instanceof ToolItem)
						((ToolItem) e.getSource()).setSelection(false);
				}
			}

