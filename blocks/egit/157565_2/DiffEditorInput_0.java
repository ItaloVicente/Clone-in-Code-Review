		if (!(obj instanceof DiffEditorInput)) {
			return false;
		}
		DiffEditorInput other = (DiffEditorInput) obj;
		if (!Objects.equals(tip.getRepository().getDirectory(),
				other.tip.getRepository().getDirectory())) {
			return false;
		}
		if (!Objects.equals(tip.getObjectId(), other.tip.getObjectId())) {
			return false;
		}
		if (base == null) {
			if (other.base != null) {
				return false;
			}
		} else {
			if (other.base == null || !Objects.equals(base.getObjectId(),
					other.base.getObjectId())) {
				return false;
			}
		}
		return true;
