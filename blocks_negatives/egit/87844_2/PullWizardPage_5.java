				this.repository, new IRefListProvider() {

					@Override
					public List<Ref> getRefList() {
						if (PullWizardPage.this.assist != null) {
							return PullWizardPage.this.assist
									.getRefsForContentAssist(false, true);
						}
						return Collections.emptyList();
