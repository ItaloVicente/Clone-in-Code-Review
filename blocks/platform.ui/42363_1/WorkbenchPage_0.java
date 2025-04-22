			boolean fixedPerspective = false;
			PerspectiveDescriptor perspectiveDesc = (PerspectiveDescriptor) getPerspectiveDesc(mperspective.getElementId());
			if (perspectiveDesc == null) {
				fixedPerspective = true;
				perspectiveDesc = fixOrphanPerspective(mperspective);
			}
			Perspective p = new Perspective(perspectiveDesc, mperspective, this);
