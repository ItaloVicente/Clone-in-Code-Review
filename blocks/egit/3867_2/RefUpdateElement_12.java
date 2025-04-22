	Status getStatus() {
		return update.getStatus();
	}

	RemoteRefUpdate getRemoteRefUpdate() {
		return update;
	}

	Ref getAdvertisedRemoteRef() {
		return result.getPushResult(uri).getAdvertisedRef(getDstRefName());
	}

	public ImageDescriptor getImageDescriptor(Object object) {
		switch (getStatus()) {
		case OK:
			if (isDelete())
				return tag ? new DecorationOverlayDescriptor(UIIcons.TAG,
						UIIcons.OVR_STAGED_REMOVE, IDecoration.TOP_RIGHT)
						: new DecorationOverlayDescriptor(UIIcons.BRANCH,
								UIIcons.OVR_STAGED_REMOVE,
								IDecoration.TOP_RIGHT);

			if (isAdd())
				return tag ? UIIcons.CREATE_TAG : UIIcons.CREATE_BRANCH;
			else
				return tag ? UIIcons.TAG : UIIcons.BRANCH;
		case UP_TO_DATE:
			return tag ? UIIcons.TAG : UIIcons.BRANCH;
		case REJECTED_NODELETE:
		case REJECTED_NONFASTFORWARD:
		case REJECTED_OTHER_REASON:
		case REJECTED_REMOTE_CHANGED:
			return tag ? new DecorationOverlayDescriptor(UIIcons.TAG,
					UIIcons.OVR_ERROR, IDecoration.TOP_RIGHT)
					: new DecorationOverlayDescriptor(UIIcons.BRANCH,
							UIIcons.OVR_ERROR, IDecoration.TOP_RIGHT);
		default:
			return super.getImageDescriptor(object);
		}
	}

	public String getLabel(Object object) {
		return getStyledText(object).getString();
