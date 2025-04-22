			Object obj = HandlerServiceImpl.lookUpHandler(context, command.getId());
			if (obj != null) {
				if (obj instanceof IHandler) {
					commandHandled = ((IHandler) obj).isHandled();
				} else {
					commandHandled = true;
				}
			}
