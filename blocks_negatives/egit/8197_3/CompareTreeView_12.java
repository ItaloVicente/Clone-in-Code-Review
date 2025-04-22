				Object result = adaptable.getAdapter(adapterType);
				if (result != null) {
					Assert.isTrue(adapterType.isInstance(result));
					return result;
				}
			}
