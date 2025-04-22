		if ("none".equals(cssTheme)) {
			appContext.set(IStylingEngine.SERVICE_NAME, new IStylingEngine() {
				@Override
				public void setClassname(Object widget, String classname) {
					WidgetElement.setCSSClass((Widget) widget, classname);
				}

				@Override
				public void setId(Object widget, String id) {
					WidgetElement.setID((Widget) widget, id);
				}

				@Override
				public void style(Object widget) {
				}

				@Override
				public CSSStyleDeclaration getStyle(Object widget) {
					return null;
				}
