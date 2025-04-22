        case TRACE:
            trace(format, argA, argB);
            break;
        case DEBUG:
            debug(format, argA, argB);
            break;
        case INFO:
            info(format, argA, argB);
            break;
        case WARN:
            warn(format, argA, argB);
            break;
        case ERROR:
            error(format, argA, argB);
            break;
        default:
            throw new Error();
