				new IRefListProvider() {
					@Override
					public List<Ref> getRefList() {
						return getRemoteRefs();
					}
				});
