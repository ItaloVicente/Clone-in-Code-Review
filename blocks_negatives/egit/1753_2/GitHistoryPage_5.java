					int type = 0;
					Object actInput = getInput();
					if (actInput instanceof IResource) {
						type = ((IResource) actInput).getType();
					}

					if (type == IResource.FILE) {
