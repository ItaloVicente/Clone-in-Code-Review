					modelsToSave = new ArrayList<>();
					Object[] objects = dlg.getResult();
					for (Object object : objects) {
						if (object instanceof Saveable) {
							modelsToSave.add((Saveable) object);
						}
					}
