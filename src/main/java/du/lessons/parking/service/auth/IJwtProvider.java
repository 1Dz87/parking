package du.lessons.parking.service.auth;

public interface IJwtProvider {

    String generateJwtToken(String login);

    Boolean validateJwtToken(String authToken);

    String getUserNameFromJwtToken(String token);
}
