				List<PlanElement> selectedRebaseTodoLines = getSelectedRebaseTodoLines();
				Collections.reverse(selectedRebaseTodoLines);
				for (PlanElement planElement : selectedRebaseTodoLines) {
					if (planElement.getElementType() != ElementType.TODO)
						return;
					view.getCurrentPlan().moveTodoEntryDown(planElement);
					mapActionItemsToSelection(view.planTreeViewer
							.getSelection());
				}
