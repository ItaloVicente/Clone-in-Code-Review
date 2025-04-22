		IVerticalRulerInfo rulerInfo = AdapterUtils.adapt(editor,
				IVerticalRulerInfo.class);

		BlameInformationControlCreator creator = new BlameInformationControlCreator(
				rulerInfo);
		info.setHoverControlCreator(creator);
		info.setInformationPresenterControlCreator(creator);

