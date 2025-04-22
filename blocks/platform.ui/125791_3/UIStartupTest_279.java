		Realm.runWithDefault(DisplayRealm.getRealm(display), new Runnable() {
			@Override
			public void run() {
				contexts[0] = UIStartupTest.super.createApplicationContext();
				contexts[0].set(IResourceUtilities.class.getName(),
						new ResourceUtility());
				contexts[0].set(IStylingEngine.class,
						new IStylingEngine() {
							@Override
							public void style(Object widget) {
							}

							@Override
							public void setId(Object widget, String id) {
							}

							@Override
							public void setClassname(Object widget,
									String classname) {
							}

							@Override
							public CSSStyleDeclaration getStyle(Object widget) {
								return null;
							}

							@Override
							public void setClassnameAndId(Object widget,
									String classname, String id) {
							}
						});
			}
