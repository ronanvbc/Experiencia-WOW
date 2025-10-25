package pe.smartgym;

public class ClienteNoExisteException extends RuntimeException {
    public ClienteNoExisteException(String msg) { super(msg); }
}
