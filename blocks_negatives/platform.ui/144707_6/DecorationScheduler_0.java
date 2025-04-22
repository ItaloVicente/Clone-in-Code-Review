				if (!elementIsCached) {
					DecorationBuilder cacheResult = new DecorationBuilder(context);
					decoratorManager.getLightweightManager().getDecorations(element, cacheResult);

					if (cacheResult.hasValue() || force) {


						internalPutResult(element, context, cacheResult.createResult());

						synchronized (pendingKey) {
							pendingUpdate.add(element);
						}
