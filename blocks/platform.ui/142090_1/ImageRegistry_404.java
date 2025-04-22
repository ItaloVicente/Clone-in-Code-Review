			if (original == toDispose) {
				refCount--;
				if (refCount == 0) {
					original.dispose();
					original = null;
				}
			} else {
				super.destroyResource(toDispose);
			}
		}

		@Override
