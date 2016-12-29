/**
 * Created by anchung on 12/26/16.
 */
public class SudoException  extends Exception{
    public SudoException() {
    }

    public SudoException(String message) {
        super(message);
    }

    public SudoException(Throwable cause) {
        super(cause);
    }

    public SudoException(String message, Throwable cause) {
        super(message, cause);
    }
}
