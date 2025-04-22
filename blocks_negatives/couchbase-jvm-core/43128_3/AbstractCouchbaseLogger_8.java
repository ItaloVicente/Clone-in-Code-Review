        case TRACE:
            trace(format, arg);
            break;
        case DEBUG:
            debug(format, arg);
            break;
        case INFO:
            info(format, arg);
            break;
        case WARN:
            warn(format, arg);
            break;
        case ERROR:
            error(format, arg);
            break;
        default:
            throw new Error();
