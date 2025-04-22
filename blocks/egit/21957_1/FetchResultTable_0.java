				if (isPruned()) {
					ImageDescriptor icon = UIIcons.BRANCH;
					if (update.getLocalName().startsWith(Constants.R_TAGS))
						icon = UIIcons.TAG;
					if (update.getLocalName().startsWith(Constants.R_NOTES))
						icon = UIIcons.NOTE;
					return new DecorationOverlayDescriptor(icon,
							UIIcons.OVR_STAGED_REMOVE, IDecoration.TOP_RIGHT);
				}
