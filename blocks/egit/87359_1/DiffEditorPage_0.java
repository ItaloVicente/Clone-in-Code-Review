	private void setOverviewAnnotations() {
		IDocumentProvider documentProvider = getDocumentProvider();
		IDocument document = documentProvider.getDocument(getEditorInput());
		if (!(document instanceof DiffDocument)) {
			return;
		}
		IAnnotationModel annotationModel = documentProvider
				.getAnnotationModel(getEditorInput());
		if (annotationModel == null) {
			return;
		}
		DiffRegion[] diffs = ((DiffDocument) document).getRegions();
		if (diffs == null || diffs.length == 0) {
			return;
		}
		Map<Annotation, Position> newAnnotations = new HashMap<>();
		for (DiffRegion region : diffs) {
			if (DiffRegion.Type.ADD.equals(region.diffType)) {
				newAnnotations.put(
						new Annotation(ADD_ANNOTATION_TYPE, true, null),
						new Position(region.getOffset(), region.getLength()));
			} else if (DiffRegion.Type.REMOVE.equals(region.diffType)) {
				newAnnotations.put(
						new Annotation(REMOVE_ANNOTATION_TYPE, true, null),
						new Position(region.getOffset(), region.getLength()));
			}
		}
		if (annotationModel instanceof IAnnotationModelExtension) {
			((IAnnotationModelExtension) annotationModel).replaceAnnotations(
					currentOverviewAnnotations, newAnnotations);
		} else {
			if (currentOverviewAnnotations != null) {
				for (Annotation existing : currentOverviewAnnotations) {
					annotationModel.removeAnnotation(existing);
				}
			}
			for (Map.Entry<Annotation, Position> entry : newAnnotations
					.entrySet()) {
				annotationModel.addAnnotation(entry.getKey(), entry.getValue());
			}
		}
		currentOverviewAnnotations = newAnnotations.keySet()
				.toArray(new Annotation[newAnnotations.size()]);
	}

