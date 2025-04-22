		if (object == this) {
			return true;
		}

		if (!(object instanceof EObjectPointer)) {
			return false;
		}

		EObjectPointer other = (EObjectPointer) object;
		if (!Objects.equals(parent, other.parent)) {
			return false;
		}

		if (!Objects.equals(name, other.name)) {
			return false;
		}

		int iThis = (index == WHOLE_COLLECTION ? 0 : index);
		int iOther = (other.index == WHOLE_COLLECTION ? 0 : other.index);
		if (iThis != iOther) {
			return false;
		}

		if (bean instanceof Number
				|| bean instanceof String
				|| bean instanceof Boolean) {
			return bean.equals(other.bean);
		}
		return bean == other.bean;
	}

	@Override
