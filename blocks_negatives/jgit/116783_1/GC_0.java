			if (!r2.isSymbolic())
				return false;
			return r1.getTarget().getName().equals(r2.getTarget().getName());
		} else {
			if (r2.isSymbolic()) {
				return false;
			}
			return Objects.equals(r1.getObjectId(), r2.getObjectId());
