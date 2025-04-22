				if (model.getCurrentSelection() instanceof IStructuredSelection) {
					IStructuredSelection sel = (IStructuredSelection) model.getCurrentSelection();
					for (Object object : sel.toArray()) {
						if (Adapters.adapt(object, adapterType) == null) {
							return false;
						}
