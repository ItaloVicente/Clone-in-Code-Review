	private PartServiceImpl partService;

	private EModelService modelService;

	private LinkedList<MPart> generalActivationHistory = new LinkedList<>();

	PartActivationHistory(PartServiceImpl partService, EModelService modelService) {
		this.partService = partService;
		this.modelService = modelService;
	}

	public void clear() {
		generalActivationHistory.clear();
	}

	void activate(MPart part, boolean activateBranch) {
		IEclipseContext context = part.getContext();
		if (activateBranch) {
			context.activateBranch();
		} else {
			IEclipseContext parent = context.getParent();
			do {
				context.activate();
				context = parent;
				parent = parent.getParent();
			} while (parent.get(MWindow.class) != null);
		}

		prepend(part);
	}

	/**
	 * Places the specified part at the end of the activation history if it is not already in the
	 * list. If it is already in the activation history, then its position will not change.
	 *
	 * @param part
	 *            the part to possibly add to the end of the activation history
	 */
	void append(MPart part) {
		if (!generalActivationHistory.contains(part)) {
			generalActivationHistory.addLast(part);
		}
	}

	/**
	 * Adds the specified part to the front of the activation history.
	 *
	 * @param part
	 *            the part to insert into the front of the activation history
	 */
	void prepend(MPart part) {
		generalActivationHistory.remove(part);
		generalActivationHistory.addFirst(part);
	}

	/**
	 * Checks to see if this element and its parents are actually being rendered.
	 */
	boolean isValid(MUIElement element) {
		if (element == null || !element.isToBeRendered() || !element.isVisible()) {
			return false;
		}

		if (element instanceof MApplication) {
			return true;
		}

		MUIElement parent = element.getParent();
		if (parent == null && element instanceof MWindow) {
			parent = (MUIElement) ((EObject) element).eContainer();
		}

		if (parent == null) {
			return isValid(partService.getLocalPlaceholder(element));
		}

		return isValid(parent);
	}

	/**
	 * Checks to see if this element and its parents are actually being rendered.
	 */
	boolean isValid(MPerspective perspective, MUIElement element) {
		if (element instanceof MApplication) {
			return true;
		} else if (element == null || !element.isToBeRendered() || !element.isVisible()) {
			return false;
		}

		MElementContainer<?> parent = element.getParent();
		if (parent == null) {
			for (MPlaceholder placeholder : modelService.findElements(perspective, null,
					MPlaceholder.class, null)) {
				if (placeholder.getRef() == element) {
					return isValid(perspective, placeholder);
				}
			}

			if (element instanceof MWindow) {
				return isValid(perspective, (MUIElement) ((EObject) element).eContainer());
			}
			return false;
		} else if (parent instanceof MGenericStack && parent.getSelectedElement() != element) {
			return false;
		}

		return isValid(perspective, parent);
	}

	/**
	 * Determines whether this element is contained within an MArea, and to return the area if it
	 * is.
	 */
	private MArea isInArea(MUIElement element) {
		if (element == null) {
			return null;
		}
		MPlaceholder placeholder = element.getCurSharedRef();
		if (placeholder == null) {
			MUIElement parent = element.getParent();
			if (parent == null) {
				parent = (MUIElement) ((EObject) element).eContainer();
			}
			return parent instanceof MApplication ? null : parent instanceof MArea ? (MArea) parent
					: isInArea(parent);
		}

		MUIElement parent = placeholder.getParent();
		if (parent == null) {
			parent = (MUIElement) ((EObject) placeholder).eContainer();
		}
		return parent instanceof MApplication ? null : parent instanceof MArea ? (MArea) parent
				: isInArea(parent);
	}

	private MPart getActivationCandidate(MPart part) {
		Collection<MPart> candidates = part.getContext().get(EPartService.class).getParts();
		return findActivationCandidate(candidates, part);
	}

	/**
	 * Finds and returns a part that is a valid candidate to be granted activation.
	 */
	private MPart findActivationCandidate(Collection<MPart> candidates, MPart currentlyActivePart) {
		candidates.remove(currentlyActivePart);

		MPlaceholder activePlaceholder = partService.getLocalPlaceholder(currentlyActivePart);
		for (MPart candidate : candidates) {
			if (isValid(candidate)) {
				MPlaceholder placeholder = partService.getLocalPlaceholder(candidate);
				MElementContainer<MUIElement> parent = placeholder == null ? candidate.getParent()
						: placeholder.getParent();
				if (parent instanceof MGenericStack) {
					MUIElement selection = parent.getSelectedElement();
					if (selection == activePlaceholder || selection == currentlyActivePart) {
						return candidate;
					}

					if (selection == candidate || selection == placeholder) {
						return candidate;
					}
				} else {
					return candidate;
				}
			}
		}
		return null;
	}

	MPart getActivationCandidate(Collection<MPart> validParts) {
		Collection<MPart> validCandidates = new ArrayList<>();
		for (MPart validPart : generalActivationHistory) {
			if (validParts.contains(validPart)) {
				validCandidates.add(validPart);
			}
		}

		MPart candidate = findActivationCandidate(validCandidates);
		if (candidate == null) {
			validParts.removeAll(validCandidates);
			return findActivationCandidate(validParts);
		}
		return candidate;
	}

	private MPart findActivationCandidate(Collection<MPart> candidates) {
		for (MPart candidate : candidates) {
			if (isValid(candidate)) {
				MPlaceholder placeholder = partService.getLocalPlaceholder(candidate);
				MElementContainer<MUIElement> parent = placeholder == null ? candidate.getParent()
						: placeholder.getParent();
				if (parent instanceof MGenericStack) {
					MUIElement selection = parent.getSelectedElement();
					if (selection == candidate || selection == placeholder) {
						return candidate;
					}
				} else {
					return candidate;
				}
			}
		}
		return null;
	}

	MPart getNextActivationCandidate(Collection<MPart> validParts, MPart part) {
		MArea area = isInArea(part);
		if (area != null) {
			MPart candidate = getSiblingActivationCandidate(part);
			if (candidate != null) {
				return candidate;
			}

			candidate = findActivationCandidate(
					modelService.findElements(area, null, MPart.class, null), part);
			if (candidate != null) {
				return candidate;
			}
		}

		Collection<MPart> validCandidates = new ArrayList<>();
		for (MPart validPart : generalActivationHistory) {
			if (validParts.contains(validPart)) {
				validCandidates.add(validPart);
			}
		}

		MPart candidate = findActivationCandidate(validCandidates, part);
		return candidate == null ? getActivationCandidate(part) : candidate;
	}

	void forget(MWindow window, MPart part, boolean full) {
		if (full) {
			generalActivationHistory.remove(part);
		} else {
			for (MPlaceholder placeholder : modelService.findElements(window, null,
					MPlaceholder.class, null)) {
				if (placeholder.getRef() == part && placeholder.isToBeRendered()) {
					return;
				}
			}

			generalActivationHistory.remove(part);
		}
	}

	MPart getActivationCandidate(MPerspective perspective) {
		for (MPart candidate : generalActivationHistory) {
			if (partService.isInContainer(perspective, candidate)
					&& isValid(perspective, candidate)) {
				return candidate;
			}
		}

		List<String> activeTag = new ArrayList<>();
		activeTag.add(EPartService.ACTIVE_ON_CLOSE_TAG);
		List<MPart> activeCandidates = modelService.findElements(perspective, null, MPart.class,
				activeTag);
		if (activeCandidates.size() > 0) {
			activeCandidates.get(0).getTags().remove(EPartService.ACTIVE_ON_CLOSE_TAG);
			MPart candidate = activeCandidates.get(0);
			if (partService.isInContainer(perspective, candidate)
					&& isValid(perspective, candidate)) {
				return candidate;
			}
		}

		Collection<MPart> candidates = perspective.getContext().get(EPartService.class).getParts();
		for (MPart candidate : candidates) {
			if (isValid(perspective, candidate)) {
				return candidate;
			}
		}
		return null;
	}

	private MPart getSiblingActivationCandidate(MPart part) {
		MPlaceholder placeholder = part.getCurSharedRef();
		MUIElement candidate = getSiblingSelectionCandidate(part, placeholder == null ? part
				: placeholder);
		return (MPart) (candidate instanceof MPlaceholder ? ((MPlaceholder) candidate).getRef()
				: candidate);
	}

	private MUIElement getSiblingSelectionCandidate(MPart part, MUIElement element) {
		List<MUIElement> siblings = element.getParent().getChildren();
		for (MPart previouslyActivatedPart : generalActivationHistory) {
			if (previouslyActivatedPart != part && isValid(previouslyActivatedPart)) {
				if (siblings.contains(previouslyActivatedPart)) {
					return previouslyActivatedPart;
				}

				MPlaceholder placeholder = partService.getLocalPlaceholder(previouslyActivatedPart);
				if (placeholder != null && placeholder.isToBeRendered()
						&& siblings.contains(placeholder)) {
					return placeholder;
				}
			}
		}
		return null;
	}

	MUIElement getSiblingSelectionCandidate(MPart part) {
		MPlaceholder placeholder = part.getCurSharedRef();
		return getSiblingSelectionCandidate(part, placeholder == null ? part : placeholder);
	}
