            container.accept(new IResourceProxyVisitor() {
                @Override
				public boolean visit(IResourceProxy proxy) {
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
						return false;
					}
                    return true;
                }
            }, IResource.NONE);
