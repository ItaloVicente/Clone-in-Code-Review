				switch (bop.getResult().getStatus()) {
				case CONFLICTS:
				case NONDELETED:
					break;
				default:
					return Activator.createErrorStatus(
							UIText.BranchAction_branchFailed, e);
