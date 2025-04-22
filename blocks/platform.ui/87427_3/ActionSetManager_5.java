			contextListener = (source, propId) -> {
				if (source instanceof IActionSetDescriptor) {
					IActionSetDescriptor desc = (IActionSetDescriptor) source;
					String id = desc.getId();
					if (propId == PROP_VISIBLE) {
						activationsById.put(id, contextService
								.activateContext(id));
					} else if (propId == PROP_HIDDEN) {
						IContextActivation act = (IContextActivation) activationsById
								.remove(id);
						if (act != null) {
							contextService.deactivateContext(act);
