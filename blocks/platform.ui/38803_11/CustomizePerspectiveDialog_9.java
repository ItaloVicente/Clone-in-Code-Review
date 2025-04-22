		String id = (String) item.getTransientData().get("ActionSet"); //$NON-NLS-1$
		if (id != null) {
			return id;
		}
		Object data = OpaqueElementUtil.getOpaqueItem(item);
		if (data == null) {
			data = item.getTransientData().get(CoolBarToTrimManager.OBJECT);
		}
		if (data instanceof IContributionItem) {
			return getActionSetID((IContributionItem) data);
		}
		return null;
