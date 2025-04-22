		Tweaklets.setDefault(InterceptContributions.KEY, new InterceptContributions() {
			@Override
			public IViewPart tweakView(Object viewContribution) {
				return (IViewPart) viewContribution;
			}

			@Override
			public IEditorPart tweakEditor(Object editorContribution) {
				return (IEditorPart) editorContribution;
			}
		});
