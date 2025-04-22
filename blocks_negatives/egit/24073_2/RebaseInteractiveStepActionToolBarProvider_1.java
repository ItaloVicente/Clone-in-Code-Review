				RebaseInteractivePlan.PlanElement selectedEntry = getSingleSelectedTodoLine(false);
				if (selectedEntry == null)
					return;
				if (selectedEntry.getElementType() != ElementType.TODO)
					return;
				view.getCurrentPlan().moveTodoEntryDown(selectedEntry);
