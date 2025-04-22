			return new ICountable() {
				@Override
				public int count() {
					return ss.size();
				}
			};
