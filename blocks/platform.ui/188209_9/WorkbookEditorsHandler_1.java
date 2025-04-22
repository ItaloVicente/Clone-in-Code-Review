	private Map<EditorReference, String> generateColumnLabelTexts(List<EditorReference> editorReferences) {
		Map<EditorReference, String> editorReferenceLabelTexts = new HashMap<>(editorReferences.size());
		Map<String, List<Entry<EditorReference, IPath>>> collisionsMap = new HashMap<>(editorReferences.size());
		editorReferences.forEach(editorReference -> {
			try {
				IPathEditorInput iPathEditorInput = getPathEditorInput(editorReference.getEditorInput());
				if (iPathEditorInput != null && iPathEditorInput.getPath() != null) {
					IPath path = iPathEditorInput.getPath();

					List<Entry<EditorReference, IPath>> referencesWithSameTitle = collisionsMap
							.get(editorReference.getTitle());
					if (referencesWithSameTitle == null) {
						referencesWithSameTitle = new ArrayList<>();
						collisionsMap.put(editorReference.getTitle(), referencesWithSameTitle);
					}

					referencesWithSameTitle.add(Map.entry(editorReference, path));
				} else {
					editorReferenceLabelTexts.put(editorReference, getWorkbenchPartReferenceText(editorReference));
				}
			} catch (PartInitException e) {
				String message = "Expected parts to be initialized"; //$NON-NLS-1$
				final IStatus status = new Status(IStatus.ERROR, WorkbenchPlugin.PI_WORKBENCH, 0, message, e);
				WorkbenchPlugin.log(message, status);
			}
		});

		for (List<Entry<EditorReference, IPath>> groupedEditorReferences : collisionsMap.values()) {
			if (groupedEditorReferences.size() == 1) {
				EditorReference editorReference = groupedEditorReferences.get(0).getKey();
				editorReferenceLabelTexts.put(editorReference, getWorkbenchPartReferenceText(editorReference));
			} else {
				Set<Integer> differingMaxSegmentsCounter = new HashSet<>();
				List<Integer> maxMatchingSegmentsList = new ArrayList<>(groupedEditorReferences.size());
				for (Entry<EditorReference, IPath> entry : groupedEditorReferences) {
					IPath path = entry.getValue();
					int maxMatchingSegments = -1;
					for (int i = 0; i < groupedEditorReferences.size(); i++) {
						IPath currentPath = groupedEditorReferences.get(i).getValue();
						if (currentPath.equals(path)) {
							continue;
						}
						int currentMatchingSegments = matchingLastSegments(path, currentPath);
						maxMatchingSegments = maxMatchingSegments < currentMatchingSegments ? currentMatchingSegments
								: maxMatchingSegments;
					}
					differingMaxSegmentsCounter.add(maxMatchingSegments);
					maxMatchingSegmentsList.add(maxMatchingSegments);
				}

				for (int i = 0; i < maxMatchingSegmentsList.size(); i++) {
					EditorReference editorReference = groupedEditorReferences.get(i).getKey();
					Integer maxMatchingSegment = maxMatchingSegmentsList.get(i);
					IPath path = groupedEditorReferences.get(i).getValue();

					String labelText = generateLabelText(editorReference, path, differingMaxSegmentsCounter,
							maxMatchingSegment);
					editorReferenceLabelTexts.put(editorReference, labelText);
				}
			}
		}
		return editorReferenceLabelTexts;
	}

	private IPathEditorInput getPathEditorInput(IEditorInput input) {
		if (input instanceof IPathEditorInput) {
			return (IPathEditorInput) input;
		}
		return Adapters.adapt(input, IPathEditorInput.class);
	}

	private String generateLabelText(EditorReference editorReference, IPath path,
			Set<Integer> differingMaxSegmentsCounter, Integer maxMatchingSegment) {
		String labelText;
		if (differingMaxSegmentsCounter.size() == 1) {
			String lastSegment = path.lastSegment();
			labelText = Path.fromPortableString(path.segment(path.segmentCount() - 1 - maxMatchingSegment))
					.append(lastSegment).toOSString();
		} else {
			labelText = path.removeFirstSegments(path.segmentCount() - 1 - maxMatchingSegment).toOSString();
		}
		return prependDirtyIndicationIfDirty(editorReference, labelText);
	}

	private String prependDirtyIndicationIfDirty(EditorReference editorReference, String labelText) {
		if (editorReference.isDirty()) {
			return "*" + labelText; //$NON-NLS-1$
		}
		return labelText;
	}

	private int matchingLastSegments(IPath path, IPath anotherPath) {
		int thisPathLen = path.segmentCount();
		int anotherPathLen = anotherPath.segmentCount();
		int max = Math.min(thisPathLen, anotherPathLen);
		int count = 0;
		for (int i = 1; i <= max; i++) {
			if (!path.segment(thisPathLen - i).equals(anotherPath.segment(anotherPathLen - i))) {
				return count;
			}
			count++;
		}
		return count;
	}

