				last.setChecked(true);
				last.setEnabled(!isAutoBuilding);
				last.setActionDefinitionId("org.eclipse.ui.project.buildLast"); //$NON-NLS-1$
				addMnemonic(last, accel++);
				new ActionContributionItem(last).fill(menu, -1);
				lastSet = last.getWorkingSet();
