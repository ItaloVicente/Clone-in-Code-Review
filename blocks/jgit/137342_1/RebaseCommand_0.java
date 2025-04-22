				numberOfDoneSteps = doneLines.size();
				RebaseTodoLine step = catchUpWithMsgNum(numberOfDoneSteps);
				if (step == null) {
					step = doneLines.get(numberOfDoneSteps - 1);
				}
				newHead = continueRebase();
