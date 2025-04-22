			handlerListener = new IHandlerListener() {
				@Override
				public void handlerChanged(HandlerEvent handlerEvent) {
					boolean enabledChanged = handlerEvent.isEnabledChanged();
					boolean handledChanged = handlerEvent.isHandledChanged();
					fireCommandChanged(new CommandEvent(Command.this, false,
							false, false, handledChanged, false, false, false,
							false, enabledChanged));
				}
