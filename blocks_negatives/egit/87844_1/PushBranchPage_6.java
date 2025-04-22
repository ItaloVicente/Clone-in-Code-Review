				this.repository, new IRefListProvider() {

					@Override
					public List<Ref> getRefList() {
						if (PushBranchPage.this.assist != null) {
							return PushBranchPage.this.assist
									.getRefsForContentAssist(false, true);
						}
						return Collections.emptyList();
