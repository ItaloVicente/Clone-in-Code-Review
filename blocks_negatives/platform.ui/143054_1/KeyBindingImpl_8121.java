			case CommandsPackageImpl.KEY_BINDING__KEY_SEQUENCE:
				return KEY_SEQUENCE_EDEFAULT == null ? keySequence != null : !KEY_SEQUENCE_EDEFAULT.equals(keySequence);
			case CommandsPackageImpl.KEY_BINDING__COMMAND:
				return command != null;
			case CommandsPackageImpl.KEY_BINDING__PARAMETERS:
				return parameters != null && !parameters.isEmpty();
