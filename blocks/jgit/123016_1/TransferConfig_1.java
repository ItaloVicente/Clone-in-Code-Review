			private boolean add(Ref ref) {
				for (String hide : hideRefs) {
					if (ref.getName().equals(hide)
							|| prefixMatch(hide
						return false;
					}
				}
				return true;
			}

