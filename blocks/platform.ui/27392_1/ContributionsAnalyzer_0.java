			} else {
				if (filtered || (!popupTarget && !parentID.equals(id))
				|| !menuContribution.isToBeRendered()) {
					continue;
				}
				includedPopups.add(menuContribution);
