			switch (node.getType()) {
			case REF:
			case TAG:
				pushWiz = new SimplePushRefWizard(node.getRepository(), (Ref)node.getObject());
				break;
			case REPO:
				pushWiz = new PushWizard(node.getRepository());
				break;
			default:
				throw new UnsupportedOperationException("type not supported!"); //$NON-NLS-1$
			}
