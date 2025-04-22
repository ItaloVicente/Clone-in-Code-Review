        try {
            container.accept(proxy -> {
			    if (!getShowDerived() && proxy.isDerived()) {
			        return false;
			    }
			    int type = proxy.getType();
			    if ((typeMask & type) != 0) {
			        if (match(proxy.getName())) {
			            IResource res = proxy.requestResource();
			            if (select(res)) {
			                resources.add(res);
			                return true;
			            }
			            return false;
			        }
			    }
			    if (type == IResource.FILE) {
