				}
				if (element instanceof RefNode) {
					executeOpenCommandWithConfirmation(element,
							((RefNode) element).getObject().getName());
				} else if (element instanceof TagNode) {
					executeOpenCommandWithConfirmation(element,
							((TagNode) element).getObject().getName());
				}
