		}
		if (target.isInstance(object)) {
			return target.cast(object);
		}
		if (object instanceof IAdaptable) {
			V adapter = Utils.getAdapter(((IAdaptable) object), target);
			if (adapter != null || object instanceof PlatformObject) {
				return adapter;
			}
		}
		Object adapted = Platform.getAdapterManager().getAdapter(object, target);
		return target.cast(adapted);
