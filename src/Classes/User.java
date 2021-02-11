package Classes;

import java.io.Serializable;

public class User implements Serializable {
    private String nickname;
    private String password;
    private boolean canRegisterToken;
    private boolean canGetCardNumFromToken;

    public User(String _nickname, String _pass, boolean _canRegisterToken, boolean _canGetCardNumFromToken) {
        this.nickname = _nickname;
        this.password = _pass;
        this.canRegisterToken = _canRegisterToken;
        this.canGetCardNumFromToken = _canGetCardNumFromToken;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public boolean canRegisterToken() {
        return canRegisterToken;
    }

    public boolean canGetCardNumFromToken() {
        return canGetCardNumFromToken;
    }
}