			return new IIterable() {
				@Override
				public Iterator iterator() {
					return ((IStructuredSelection) sel).iterator();
				}
			};
