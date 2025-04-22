			handlerListener = new IHandlerListener() {
				@Override
				public void handlerChanged(HandlerEvent handlerEvent) {
					fireHandlerChanged(new HandlerEvent(HandlerProxy.this,
							handlerEvent.isEnabledChanged(), handlerEvent
									.isHandledChanged()));
				}
			};
