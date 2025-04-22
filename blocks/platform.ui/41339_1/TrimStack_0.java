
			MPerspective persp = null;
			List<MPerspective> perspectives = modelService.findElements(ps.get(0), perspId,
					MPerspective.class, null);
			if (perspectives != null && !perspectives.isEmpty()) {
				persp = perspectives.get(0);
			}

