		} else {
			if (checkState != null) {
				Object[] visible = filter(checkState.toArray());
				for (int i = 0; i < visible.length; i++) {
					checkState.remove(visible[i]);
				}
			}
