				@Override
				protected void updateWidget(Widget widget, Counter element) {
					((Label) widget).setText(element.getValue() + "");
					requestLayout((Label) widget);
				}
			};
			GridLayoutFactory.fillDefaults().numColumns(10).generateLayout(composite);
