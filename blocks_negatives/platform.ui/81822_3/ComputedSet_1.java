			fireSetChange(new SetDiff<E>() {
				SetDiff<E> delegate;

				private SetDiff<E> getDelegate() {
					if (delegate == null)
						delegate = Diffs.computeSetDiff(oldSet, getSet());
					return delegate;
				}

				@Override
				public Set<E> getAdditions() {
					return getDelegate().getAdditions();
				}

				@Override
				public Set<E> getRemovals() {
					return getDelegate().getRemovals();
				}
			});
