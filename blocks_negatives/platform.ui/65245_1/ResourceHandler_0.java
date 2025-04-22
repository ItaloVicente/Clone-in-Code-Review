		this.context.set(MApplication.class, appElement);

		MPartSashContainer sash = (MPartSashContainer) ((MPerspectiveStack) appElement.getChildren().get(0)
				.getChildren().get(0)).getChildren().get(0).getChildren().get(0);
		MPartSashContainer sash2 = (MPartSashContainer) sash.getChildren().get(0);
		MPartStack stack = (MPartStack) sash2.getChildren().get(0);
		List<MStackElement> list = stack.getChildren();
		System.out.println(list);
