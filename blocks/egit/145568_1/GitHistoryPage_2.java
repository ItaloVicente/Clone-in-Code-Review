			boolean objChanged = false;
			if (newSelectedObj != null && newSelectedObj != selectedObj) {
				objChanged = selectedObj == null
						|| !newSelectedObj.equals(selectedObj);
			}
			selectedObj = newSelectedObj;

			if (forceNewWalk || repoChanged || objChanged
