		case AdvancedPackageImpl.PLACEHOLDER__REF:
			if (resolve)
				return getRef();
			return basicGetRef();
		case AdvancedPackageImpl.PLACEHOLDER__CLOSEABLE:
			return isCloseable();
