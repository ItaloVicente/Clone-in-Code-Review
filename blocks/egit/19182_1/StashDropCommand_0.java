				String message;
				if (nodes.size() > 1)
					message = MessageFormat.format(
							UIText.StashDropCommand_confirmMultiple,
							Integer.toString(nodes.size()));
				else
					message = MessageFormat.format(
							UIText.StashDropCommand_confirmSingle,
							Integer.toString(nodes.get(0).getIndex()));

