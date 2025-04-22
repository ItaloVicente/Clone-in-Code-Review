	public static void XXXgatherMenuContributions(final MMenu menuModel,
			final List<MMenuContribution> menuContributionList, final String id,
			final ArrayList<MMenuContribution> toContribute, final ExpressionContext eContext,
			boolean includePopups) {
		if (id == null || id.length() == 0) {
			return;
		}
		ArrayList<String> popupIds = new ArrayList<>();
		if (includePopups) {
			popupIds.add(id);
			for (String tag : menuModel.getTags()) {
					if (!popupIds.contains(tmp)) {
						popupIds.add(tmp);
					}
				}
			}
		}
		ArrayList<MMenuContribution> includedPopups = new ArrayList<>();
		for (MMenuContribution menuContribution : menuContributionList) {
			String parentID = menuContribution.getParentId();
			if (parentID == null) {
				continue;
			}
			boolean popupTarget = includePopups && popupIds.contains(parentID);
			boolean popupAny = includePopups && menuModel instanceof MPopupMenu
					&& POPUP_PARENT_ID.equals(parentID);
			boolean filtered = isFiltered(menuModel, menuContribution, includePopups);
			if (!filtered && menuContribution.isToBeRendered() && popupAny) {
				toContribute.add(menuContribution);
			} else {
				if (filtered || (!popupTarget && !parentID.equals(id))
				|| !menuContribution.isToBeRendered()) {
					continue;
				}
				includedPopups.add(menuContribution);
			}
		}
		toContribute.addAll(includedPopups);
	}

