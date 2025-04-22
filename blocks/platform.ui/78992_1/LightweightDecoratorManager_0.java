			final Object element;

			public RunnableData(Object object, DecorationBuilder builder, LightweightDecoratorDefinition definition) {
				this.element = object;
				this.builder = builder;
				this.decorator = definition;
			}

			boolean isConsistent() {
				return builder != null && decorator != null && element != null;
			}
		}

		private volatile RunnableData data = new RunnableData(null, null, null);
