		System.out.println("StackDropAgent.drop(...)");
		if (dragElement instanceof MPartStack) {
			if (info.curElement == dragElement) {
				System.out.println("Doing nothing: curElement == dragElement");
				return true;
			}
		}

