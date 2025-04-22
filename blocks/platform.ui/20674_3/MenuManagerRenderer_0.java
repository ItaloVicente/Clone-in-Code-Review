			if (itemModel instanceof MMenuSeparator
					&& itemModel.getTags().contains("Opaque")) { //$NON-NLS-1$
				oldSeps.add((MMenuSeparator) itemModel);
			} else if (itemModel instanceof MDirectMenuItem
					&& itemModel.getTags().contains("Opaque")) { //$NON-NLS-1$
				oldModelItems.add((MDirectMenuItem) itemModel);
			} else if (itemModel instanceof MMenu
					&& itemModel.getTags().contains("Opaque")) { //$NON-NLS-1$
				oldMenus.add((MMenu) itemModel);
