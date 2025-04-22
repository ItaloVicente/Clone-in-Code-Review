        case TRACE:
            trace(msg);
            break;
        case DEBUG:
            debug(msg);
            break;
        case INFO:
            info(msg);
            break;
        case WARN:
            warn(msg);
            break;
        case ERROR:
            error(msg);
            break;
        default:
            throw new Error();
