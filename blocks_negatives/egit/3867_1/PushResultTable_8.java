	private class UpdateStatusLabelProvider extends ColumnLabelProvider {
		private final URIish uri;

		UpdateStatusLabelProvider(final URIish uri) {
			this.uri = uri;
		}

		@Override
		public String getText(Object element) {
			final RefUpdateElement rue = (RefUpdateElement) element;
			if (!rue.isSuccessfulConnection(uri))
				return UIText.PushResultTable_statusConnectionFailed;

			final RemoteRefUpdate rru = rue.getRemoteRefUpdate(uri);
			switch (rru.getStatus()) {
			case OK:
				if (rru.isDelete())
					return UIText.PushResultTable_statusOkDeleted;

				final Ref oldRef = rue.getAdvertisedRemoteRef(uri);
				if (oldRef == null) {
					if (rue.getDstRefName().startsWith(Constants.R_TAGS))
						return UIText.PushResultTable_statusOkNewTag;
					return UIText.PushResultTable_statusOkNewBranch;
				}

				return safeAbbreviate(oldRef.getObjectId())
						+ safeAbbreviate(rru.getNewObjectId());
			case UP_TO_DATE:
				return UIText.PushResultTable_statusUpToDate;
			case NON_EXISTING:
				return UIText.PushResultTable_statusNoMatch;
			case REJECTED_NODELETE:
			case REJECTED_NONFASTFORWARD:
			case REJECTED_REMOTE_CHANGED:
				return UIText.PushResultTable_statusRejected;
			case REJECTED_OTHER_REASON:
				return UIText.PushResultTable_statusRemoteRejected;
			default:
				throw new IllegalArgumentException(NLS.bind(
						UIText.PushResultTable_statusUnexpected, rru
								.getStatus()));
			}
		}

		private String safeAbbreviate(ObjectId id) {
			String abbrev = abbrevations.get(id);
			if (abbrev == null) {
				try {
					abbrev = reader.abbreviate(id).name();
				} catch (IOException cannotAbbreviate) {
					abbrev = id.name();
				}
				abbrevations.put(id, abbrev);
			}
			return abbrev;
		}

		@Override
		public Image getImage(Object element) {
			final RefUpdateElement rue = (RefUpdateElement) element;
			if (!rue.isSuccessfulConnection(uri))
				return imageRegistry.get(IMAGE_DELETE);
			final Status status = rue.getRemoteRefUpdate(uri).getStatus();
			switch (status) {
			case OK:
			case UP_TO_DATE:
			case NON_EXISTING:
			case REJECTED_NODELETE:
			case REJECTED_NONFASTFORWARD:
			case REJECTED_REMOTE_CHANGED:
			case REJECTED_OTHER_REASON:
				return imageRegistry.get(IMAGE_DELETE);
			default:
				throw new IllegalArgumentException(NLS.bind(
						UIText.PushResultTable_statusUnexpected, status));
			}
		}

		@Override
		public String getToolTipText(Object element) {
			final RefUpdateElement rue = (RefUpdateElement) element;
			if (!rue.isSuccessfulConnection(uri))
				return rue.getErrorMessage(uri);

			final RemoteRefUpdate rru = rue.getRemoteRefUpdate(uri);
			final Ref oldRef = rue.getAdvertisedRemoteRef(uri);
			switch (rru.getStatus()) {
			case OK:
				if (rru.isDelete())
					return NLS.bind(UIText.PushResultTable_statusDetailDeleted,
							safeAbbreviate(oldRef.getObjectId()));
				if (oldRef == null)
					return null;
				if (rru.isFastForward())
					return UIText.PushResultTable_statusDetailFastForward;
				return UIText.PushResultTable_statusDetailForcedUpdate;
			case UP_TO_DATE:
				return null;
			case NON_EXISTING:
				return UIText.PushResultTable_statusDetailNonExisting;
			case REJECTED_NODELETE:
				return UIText.PushResultTable_statusDetailNoDelete;
			case REJECTED_NONFASTFORWARD:
				return UIText.PushResultTable_statusDetailNonFastForward;
			case REJECTED_REMOTE_CHANGED:
				final Ref remoteRef = oldRef;
				final String curVal;
				if (remoteRef == null)
					curVal = UIText.PushResultTable_refNonExisting;
				else
					curVal = safeAbbreviate(remoteRef.getObjectId());

				final ObjectId expectedOldObjectId = rru
						.getExpectedOldObjectId();
				final String expVal;
				if (expectedOldObjectId.equals(ObjectId.zeroId()))
					expVal = UIText.PushResultTable_refNonExisting;
				else
					expVal = safeAbbreviate(expectedOldObjectId);
				return NLS.bind(UIText.PushResultTable_statusDetailChanged,
						curVal, expVal);
			case REJECTED_OTHER_REASON:
				return rru.getMessage();
			default:
				throw new IllegalArgumentException(NLS.bind(
						UIText.PushResultTable_statusUnexpected, rru
								.getStatus()));
			}
		}
	}
