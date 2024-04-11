package jovelAsirot.U5W2D4.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(Long id) {
        super("Record with id: " + id + " was not found ꜀( ˊ̠˂˃ˋ̠ )꜆.");
    }
}
