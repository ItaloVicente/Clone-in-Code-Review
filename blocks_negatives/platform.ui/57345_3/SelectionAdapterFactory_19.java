			return new IIterable<Object>() {
				@Override
				public Iterator<Object> iterator() {
					return ((IStructuredSelection) sel).iterator();
				}
			};
