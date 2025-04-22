
							theChild.setContainerData(container.getContainerData());
							container.getChildren().remove(theChild);
							parentContainer.getChildren().add(index, theChild);
							container.setToBeRendered(false);
							parentContainer.getChildren().remove(container);
