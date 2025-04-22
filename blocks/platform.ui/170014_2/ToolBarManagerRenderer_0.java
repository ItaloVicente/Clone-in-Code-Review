			String cssClass = WidgetElement.getCSSClass(toolbarComposite);
			if (cssClass != null && !cssClass.isEmpty()) {
				if (!cssClass.contains("ToolbarComposite")) {//$NON-NLS-1$
					cssClass = cssClass + " ToolbarComposite"; //$NON-NLS-1$
				}
			} else {
				cssClass = "ToolbarComposite"; //$NON-NLS-1$
			}
			engine.setClassname(toolbarComposite, cssClass);
