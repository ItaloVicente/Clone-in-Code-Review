					RefNode refNode = (RefNode) element;
					String currentBranch = null;
					try {
						currentBranch = refNode.getRepository()
								.getFullBranch();
					} catch (IOException e) {
					}
					String targetBranch = ((RefNode) element).getObject()
							.getName();
					if (!targetBranch.equals(currentBranch)) {
						executeOpenCommandWithConfirmation(element,
								targetBranch);
					}
