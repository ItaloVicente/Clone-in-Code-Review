				new IRefListProvider() {
					@Override
					public List<Ref> getRefList() {
						return assistProvider.getRefsForContentAssist(true, pushMode);
					}
				});
