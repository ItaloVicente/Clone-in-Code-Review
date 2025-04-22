        case TRACE:
            trace(format, arguments);
            break;
        case DEBUG:
            debug(format, arguments);
            break;
        case INFO:
            info(format, arguments);
            break;
        case WARN:
            warn(format, arguments);
            break;
        case ERROR:
            error(format, arguments);
            break;
        default:
            throw new Error();
