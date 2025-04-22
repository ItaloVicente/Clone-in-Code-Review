			}
			break;
		}
		case ADDITIONALREF: {
			Ref ref = refCache.findAdditional(repository,
					((Ref) node.getObject()).getName());
			if (ref != null) {
				decorateRefIcon(repository, ref, decoration);
			}
			break;
		}
		case REF: {
			Ref ref = refCache.exact(repository,
					((Ref) node.getObject()).getName());
			if (ref != null) {
				decorateRefIcon(repository, ref, decoration);
			}
			break;
		}
		default:
			break;
		}
	}

	private void decorateRefIcon(@NonNull Repository repository, Ref ref,
			IDecoration decoration) {
		String branchName = DecoratorRepositoryStateCache.INSTANCE
				.getFullBranchName(repository);
		if (branchName == null) {
			return;
		}
		String refName = ref.getName();
		Ref leaf = ref.getLeaf();

		String compareString = null;
		if (refName.startsWith(Constants.R_HEADS)) {
			compareString = refName;
		} else if (refName.startsWith(Constants.R_REMOTES)) {
			ObjectId objectId = leaf.getObjectId();
			if (objectId != null) {
				String leafName = objectId.getName();
				if (leafName.equals(branchName)) {
