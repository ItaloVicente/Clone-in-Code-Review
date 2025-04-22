        			newSet.removeAll(activitySet);
        			activitySet = newSet;
        		}

        		workingCopy.setEnabledActivityIds(activitySet);
        	}
        }));
        categoryViewer = new CheckboxTableViewer(table);
