			boolean objChanged = false;
			if (newSelectedObj != null && newSelectedObj != selectedObj) {
				objChanged = !newSelectedObj.equals(selectedObj);
			}
			selectedObj = newSelectedObj;

			if (forceNewWalk || repoChanged || objChanged
