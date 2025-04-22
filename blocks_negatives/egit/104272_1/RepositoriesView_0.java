				if (element instanceof RefNode)
					executeOpenCommandWithConfirmation(((RefNode) element)
							.getObject().getName());
				if (element instanceof TagNode)
					executeOpenCommandWithConfirmation(((TagNode) element)
							.getObject().getName());
