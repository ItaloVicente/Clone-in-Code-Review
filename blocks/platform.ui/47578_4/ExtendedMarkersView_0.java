
			private boolean hasPendingChanges() {
				boolean[] changeFlags = builder.readChangeFlags();
				for (boolean b : changeFlags) {
					if (b) {
						return true;
					}
				}
				return false;
			}
