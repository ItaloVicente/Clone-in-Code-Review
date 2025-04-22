	public ITrimManager getTrimManager() {
		if (trimManager == null) {
			trimManager = new ITrimManager() {
				@Override
				public void addTrim(int areaId, IWindowTrim trim) {
				}

				@Override
				public void addTrim(int areaId, IWindowTrim trim, IWindowTrim beforeMe) {
				}

				@Override
				public void removeTrim(IWindowTrim toRemove) {
				}

				@Override
				public IWindowTrim getTrim(String id) {
					return null;
				}

				@Override
				public int[] getAreaIds() {
					return null;
				}

				@Override
				public List getAreaTrim(int areaId) {
					return null;
				}

				@Override
				public void updateAreaTrim(int id, List trim, boolean removeExtra) {
				}

				@Override
				public List getAllTrim() {
					return null;
				}

				@Override
				public void setTrimVisible(IWindowTrim trim, boolean visible) {
				}

				@Override
				public void forceLayout() {
				}
			};
		}
		return trimManager;
	}

