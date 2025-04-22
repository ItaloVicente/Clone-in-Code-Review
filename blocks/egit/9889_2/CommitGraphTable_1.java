		private boolean hasMultipleRefNodes() {
			try {
				Map<String, Ref> branches = input.getRepository()
						.getRefDatabase().getRefs(Constants.R_HEADS);
				int count = 0;
				for (Ref branch : branches.values()) {
					if (branch.getLeaf().getObjectId()
							.equals(((RevCommit) ((IStructuredSelection) selectionProvider
									.getSelection()).getFirstElement()).getId()))
						count++;
				}
				return (count > 1);

			} catch (IOException e) {
			}
			return false;
		}

