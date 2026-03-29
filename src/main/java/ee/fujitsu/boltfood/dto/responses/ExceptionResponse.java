package ee.fujitsu.boltfood.dto.responses;
public record ExceptionResponse(
        int status,
        String error,
        String message
) {
}
