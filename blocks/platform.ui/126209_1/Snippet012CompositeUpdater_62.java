				@Override
				protected void updateWidget(Widget widget, Object element) {
					((Label) widget).setText(((Counter) element).getValue() + "");
					requestLayout((Label) widget);
				}
			};
			GridLayoutFactory.fillDefaults().numColumns(10).generateLayout(composite);
