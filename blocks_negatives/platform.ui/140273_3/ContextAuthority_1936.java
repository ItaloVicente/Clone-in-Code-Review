						 * In the case where a dispose has happened, we are
						 * expecting an activation event to arrive at some point
						 * in the future. If we process the submissions now,
						 * then we will update the activeShell before
						 * checkWindowType is called. This means that dialogs
						 * won't be recognized as dialogs.
