		ICompositeSideEffect compositeSideEffect = (ICompositeSideEffect) disposableWidget
				.getData(ICompositeSideEffect.class.getName());
		if (compositeSideEffect == null) {
			ICompositeSideEffect newCompositeSideEffect = ICompositeSideEffect.create();
			disposableWidget.setData(ICompositeSideEffect.class.getName(), newCompositeSideEffect);
			disposableWidget.addDisposeListener(e -> newCompositeSideEffect.dispose());
			compositeSideEffect = newCompositeSideEffect;
		}
