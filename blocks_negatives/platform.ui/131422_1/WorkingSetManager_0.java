			} catch (IOException e) {
				handleInternalError(
						e,
						WorkbenchMessages.ProblemRestoringWorkingSetState_title,
						WorkbenchMessages.ProblemRestoringWorkingSetState_message);
			} catch (WorkbenchException e) {
