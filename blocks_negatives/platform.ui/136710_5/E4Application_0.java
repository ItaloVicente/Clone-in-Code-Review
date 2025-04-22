
		appContext.set(IStylingEngine.class, new IStylingEngine() {
			@Override
			public void setClassname(Object widget, String classname) {
			}

			@Override
			public void setId(Object widget, String id) {
			}

			@Override
			public void style(Object widget) {
			}

			@Override
			public CSSStyleDeclaration getStyle(Object widget) {
				return null;
			}

			@Override
			public void setClassnameAndId(Object widget, String classname, String id) {
			}
		});

