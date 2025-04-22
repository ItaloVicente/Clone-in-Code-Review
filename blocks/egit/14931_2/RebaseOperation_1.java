					if (interactiveHandler != null)
						cmd.runInteractively(interactiveHandler, true);

					if (operation == Operation.BEGIN){
						if (ref == null)
							return; // throw Exception instead?
