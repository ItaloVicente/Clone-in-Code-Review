				List<PlanElement> selectedRebaseTodoLines = getSelectedRebaseTodoLines();
				for (PlanElement planElement : selectedRebaseTodoLines) {
					if (planElement.getElementType() != ElementType.TODO)
						return;

					if (!RebaseInteractivePreferences.isOrderReversed())
						view.getCurrentPlan().moveTodoEntryUp(planElement);
					else
						view.getCurrentPlan().moveTodoEntryDown(planElement);

					mapActionItemsToSelection(view.planTreeViewer
							.getSelection());
				}
