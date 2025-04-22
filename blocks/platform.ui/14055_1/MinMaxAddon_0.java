				for (MUIElement ele : minimizedElements) {
					String fullId = ele.getElementId() + perspId;

					for (MToolControl tc : tcList) {
						if (fullId.equals(tc.getElementId())) {
							tc.setToBeRendered(true);
						}
