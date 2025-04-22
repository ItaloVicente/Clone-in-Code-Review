				List<PlanElement> selectedRebaseTodoLines = getSelectedRebaseTodoLines();
				for (PlanElement planElement : selectedRebaseTodoLines) {
					if (planElement.getElementType() != ElementType.TODO)
						return;
					view.getCurrentPlan().moveTodoEntryUp(planElement);
					mapActionItemsToSelection(view.planTreeViewer
							.getSelection());
				}
