					try {
						if (node.getRepository().getFullBranch().equals(
								ref.getName())) {
							checkout.setEnabled(false);
						}
					} catch (IOException e2) {
					}
