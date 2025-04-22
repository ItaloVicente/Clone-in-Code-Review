                        });
                        break;
                    case RETRY:
                        retry(event);
                        break;
                    default:
                        throw new UnsupportedOperationException("The ResponseStatus " + status + " is not supported.");
