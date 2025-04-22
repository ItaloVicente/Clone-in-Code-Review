					Object data = item.getData();
					if (data instanceof IProject) {
						IProject project = (IProject) data;
						CloseResourceAction cra = new CloseResourceAction(() -> null);
						cra.selectionChanged(new StructuredSelection(project));
						cra.run();
