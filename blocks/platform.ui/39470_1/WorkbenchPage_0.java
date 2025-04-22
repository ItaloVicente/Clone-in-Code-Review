		Perspective oldPersp = getPerspective(persp);
		Perspective dummyPersp = getPerspective(dummyPerspective);
		updateActionSets(oldPersp, dummyPersp);
		oldPersp.getAlwaysOnActionSets().clear();
		oldPersp.getAlwaysOnActionSets().addAll(dummyPersp.getAlwaysOnActionSets());
		oldPersp.getAlwaysOffActionSets().clear();
		oldPersp.getAlwaysOffActionSets().addAll(dummyPersp.getAlwaysOffActionSets());

