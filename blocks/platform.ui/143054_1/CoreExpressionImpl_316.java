		case UiPackageImpl.CORE_EXPRESSION__CORE_EXPRESSION_ID:
			return CORE_EXPRESSION_ID_EDEFAULT == null ? coreExpressionId != null
					: !CORE_EXPRESSION_ID_EDEFAULT.equals(coreExpressionId);
		case UiPackageImpl.CORE_EXPRESSION__CORE_EXPRESSION:
			return CORE_EXPRESSION_EDEFAULT == null ? coreExpression != null
					: !CORE_EXPRESSION_EDEFAULT.equals(coreExpression);
