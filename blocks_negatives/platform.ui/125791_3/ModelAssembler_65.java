					commands.add(() -> {
						if (internalFeature.isMany()) {
							@SuppressWarnings("unchecked")
							List<Object> l = (List<Object>) interalTarget.eGet(internalFeature);
							int index = l.indexOf(internalImportObject);
							if (index >= 0) {
								l.set(index, internalElment);
